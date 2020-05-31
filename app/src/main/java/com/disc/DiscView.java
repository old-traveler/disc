package com.disc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import java.util.List;
import java.util.Stack;

/**
 * Created by hyc on 2018/1/10 15:47
 */

public class DiscView extends View {

  private int radius;

  private int centerRadius;

  private int total;

  private RectF discRect;

  private Paint mPaint;

  private List<DataItem> items;

  private int itemHeight = 40;

  private int margin = 30;

  private int textSize = 30;

  private Paint textPaint;
  private int top = 0;

  private PorterDuffXfermode mode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

  public DiscView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public DiscView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    mPaint = new Paint();
    mPaint.setAntiAlias(true);

    textPaint = new Paint();
    textPaint.setAntiAlias(true);
    textPaint.setColor(Color.parseColor("#8f8e8e"));
    textPaint.setTextSize(textSize);
  }

  public DiscView(Context context) {
    this(context, null);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightSize = measure(widthSize);
    radius = widthSize / 4;
    centerRadius = widthSize / 7;
    setMeasuredDimension(widthSize, heightSize);
  }

  int measure(int width) {
    int top = 0;
    int bottom = 0;
    float[] angle = new float[items.size()];
    Point[] point = new Point[items.size()];
    Stack<Point> stack = new Stack<>();
    Point endPoint = null;
    for (int i = 0; i < items.size(); i++) {
      angle[i] = items.get(i).getValue() * 1.0f / total * 360.0f;
      point[i] = getPointByAngle(angle[i] / 2 + (i > 0 ? angle[i - 1] : 0));
      endPoint = getEndPoint(point[i], endPoint, width);
      if (i > 0) angle[i] += angle[i - 1];
      if ((point[i].x < 0 && point[i].y > 0) || (point[i].x > 0 && point[i].y < 0)) {
        if (!stack.isEmpty() && !isSameQuadrant(stack.peek(), point[i])) {
          endPoint = point[i - 1];
        }
        stack.push(point[i]);
      } else if (!stack.isEmpty()) {
        endPoint = getEndPoint(point[i], point[i - 1], width);
      }
      top = Math.min(top, endPoint.y);
      bottom = Math.max(bottom, endPoint.y);
    }
    this.top = top;
    if (2 * top + bottom > 0) {
      return bottom - top * 2;
    }
    return bottom - top;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    mPaint.setXfermode(mode);
    drawItem(canvas);
    drawInnerCircle(canvas);
    mPaint.setXfermode(null);
    drawItemInfo(canvas);
  }

  private void drawInnerCircle(Canvas canvas) {
    mPaint.setColor(Color.WHITE);
    canvas.drawCircle(0, 0, centerRadius, mPaint);
  }

  private void drawItemInfo(Canvas canvas) {
    mPaint.setStrokeWidth(2);
    float[] angle = new float[items.size()];
    Point[] point = new Point[items.size()];
    Stack<Point> stack = new Stack<>();
    Point endPoint = null;
    for (int i = 0; i < items.size(); i++) {
      angle[i] = items.get(i).getValue() * 1.0f / total * 360.0f;
      point[i] = getPointByAngle(angle[i] / 2 + (i > 0 ? angle[i - 1] : 0));
      endPoint = getEndPoint(point[i], endPoint, getWidth());
      if (i > 0) angle[i] += angle[i - 1];
      if ((point[i].x < 0 && point[i].y > 0) || (point[i].x > 0 && point[i].y < 0)) {
        if (!stack.isEmpty() && !isSameQuadrant(stack.peek(), point[i])) {
          reverseDrawItemInfo(stack, i - 1, canvas, null);
          endPoint = point[i - 1];
        }
        stack.push(point[i]);
        if (i == items.size() - 1) {
          reverseDrawItemInfo(stack, i, canvas, point[0]);
        }
        continue;
      } else if (!stack.isEmpty()) {
        reverseDrawItemInfo(stack, i - 1, canvas, null);
        endPoint = getEndPoint(point[i], point[i - 1], getWidth());
      }
      drawDataLine(point[i], endPoint, canvas, items.get(i));
    }
  }

  private void reverseDrawItemInfo(Stack<Point> stack, int position, Canvas canvas,
      Point nextPoint) {
    Point curPoint;
    while (!stack.isEmpty()) {
      curPoint = stack.pop();
      nextPoint = getEndPoint(curPoint, nextPoint, getWidth());
      drawDataLine(curPoint, nextPoint, canvas, items.get(position--));
    }
  }

  private Point getEndPoint(Point point, Point prePoint, int width) {
    if (prePoint != null && point.x * prePoint.x >= 0) {
      int distance = Math.abs(prePoint.y - point.y);
      if (distance < 3 * itemHeight / 2 || (point.y >= 0 && prePoint.y > point.y) || (point.y < 0
          && prePoint.y < point.y)) {
        Point endPoint = new Point();
        endPoint.x = point.x > 0 ? width / 2 - margin : -width / 2 + margin;
        endPoint.y = point.y >= 0 ? prePoint.y + 2 * itemHeight : prePoint.y - 2 * itemHeight;
        return endPoint;
      } else {
        return getEndPoint(point, width);
      }
    } else {
      return getEndPoint(point, width);
    }
  }

  private boolean isSameQuadrant(Point point, Point prePoint) {
    return point.x * prePoint.x >= 0 && point.y * prePoint.y >= 0;
  }

  private Point getEndPoint(Point point, int width) {
    Point endPoint = new Point();
    endPoint.x = point.x > 0 ? width / 2 - margin : -width / 2 + margin;
    if (Math.abs(point.y) > 5 * radius / 6) {
      endPoint.y = point.y + (point.y > 0 ? itemHeight : -itemHeight);
    } else {
      endPoint.y = point.y;
    }
    Log.e("TTT", point + "  " + endPoint);
    return endPoint;
  }

  private Point getPointByAngle(float angle) {
    Point point = new Point();
    point.x = (int) (Math.cos(Math.toRadians(angle)) * radius * 8 / 7);
    point.y = (int) (Math.sin(Math.toRadians(angle)) * radius * 8 / 7);
    return point;
  }

  private void drawItem(Canvas canvas) {
    float start = 0;
    float need = 0;
    int heightCenter = radius - top / 2;
    canvas.translate(getWidth() / 2, (heightCenter + (Math.abs(top) >> 3)));
    discRect = new RectF(-radius, -radius, radius, radius);
    for (DataItem item : items) {
      mPaint.setColor(item.getColor());
      mPaint.setStrokeWidth(20);
      start += need;
      need = item.getValue() * 1.0f / total * 360.0f;
      canvas.drawArc(discRect, start, need, true, mPaint);
    }
  }

  private void drawDataLine(Point start, Point end, Canvas canvas, DataItem item) {
    Log.e("AAA", start + "  " + end);
    mPaint.setColor(item.getColor());
    canvas.drawCircle(start.x, start.y, radius / 20, mPaint);
    if (start.y != end.y) {
      Point temp = new Point(0, end.y);
      int distance = Math.abs(start.y - end.y);
      if (distance > itemHeight) {
        distance = itemHeight;
      }
      if (start.x > 0) {
        temp.x = start.x + distance;
      } else {
        temp.x = start.x - distance;
      }
      canvas.drawLine(start.x, start.y, temp.x, temp.y, mPaint);
      start = temp;
    }
    drawText(canvas, end, item);
    canvas.drawLine(start.x, start.y, end.x, end.y, mPaint);
  }

  private void drawText(Canvas canvas, Point point, DataItem item) {
    drawText(canvas, point, item.getTopText(), true);
    drawText(canvas, point, item.getBottomText(), false);
  }

  private void drawText(Canvas canvas, Point point, String text, boolean isTop) {
    if (text != null) {
      float x = point.x + (point.x > 0 ? -getAlignLength(text) : 0);
      float y;
      if (isTop) {
        y = point.y - (itemHeight - textSize) / 2;
      } else {
        y = point.y + (itemHeight + textSize) / 2;
      }
      canvas.drawText(text, x, y, textPaint);
    }
  }

  private float getAlignLength(String text) {
    return textPaint.measureText(text);
  }

  public void setItems(List<DataItem> items) {
    this.items = items;
    total = 0;
    for (DataItem item : items) {
      total += item.getValue();
    }
    invalidate();
  }
}
