
# 一个圆环统计控件

## 说明

使用对象DataItem作为数据项加载

加载方式如下：
```
DiscView discView = findViewById(R.id.disc);
List<DataItem> items = new ArrayList<>();
items.add(new DataItem(1,"百度","26.56",getColor(R.color.red)));
items.add(new DataItem(1,"腾讯","32.45",getColor(R.color.green)));
items.add(new DataItem(1,"美团","12.36",getColor(R.color.red)));
items.add(new DataItem(23,"Google","56.23",getColor(R.color.black)));
items.add(new DataItem(1,"沃尔玛","45.56",getColor(R.color.red)));
items.add(new DataItem(35,"阿里巴巴","45.56",getColor(R.color.blue)));
items.add(new DataItem(2,"华为","45.56",getColor(R.color.black)));
items.add(new DataItem(3,"斗鱼","45.56",getColor(R.color.blue)));
items.add(new DataItem(4,"虎牙","45.56",getColor(R.color.yellow)));
items.add(new DataItem(24,"京东","35.56",getColor(R.color.green)));
items.add(new DataItem(23,"Windows","37.25",getColor(R.color.yellow)));
items.add(new DataItem(12,"头条","334.25",getColor(R.color.blue)));
items.add(new DataItem(13,"IBM","37.25",getColor(R.color.black)));
items.add(new DataItem(2,"甲骨文","30.25",getColor(R.color.yellow)));
discView.setItems(items);
```

## 效果

<div style="align: center">
<img src="https://github.com/old-traveler/disc/blob/master/img/show.jpg"/>
</div>
