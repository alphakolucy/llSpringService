var apiUrl = "http://localhost:8080";
var pointrotiox=1800;//点坐标x缩放比例
var pointrotioy=3100;//点坐标y缩放比例
//水质标准
var waterstanard = {
	"dissolvedSolids": 1000,
	"co2": 500,
	"hydrothion": 2,
	"hsio": 50,
	"hbo2": 35,
	"br2": 5,
	"i2": 10,
	"fe": 0.7,
	"asa": 110,
	"rn": 25,
};
//详情显示字段
var showproperties = {
	"codeNumber": "编号",
	"address": "位置名称",
	"x": "x",
	"y": "y",
	"z": "海拔",
	"holeDepth": "孔深",
	"ph": "PH",
	"waterTemperature": "水温",
	"waterInflow": "涌水量",
	"trepanning": "开孔/出露层位",
	"waterOutlet": "出水段",
	"dissolvedSolids": "溶解性总固体",
	"co2": "二氧化碳",
	"hydrothion": "总硫化氢",
	"hsio": "偏硅酸",
	"hbo2": "偏硼酸",
	"br2": "溴",
	"i2": "碘",
	"fe": "总铁",
	"asa": "砷",
	"rn": "氡",
	"hydrochemicalType": "水化学类型"
};
//导出数据文件表头
var pointtabletitles = {
	"id": "ID",
	"codeNumber": "编号",
	"address": "位置名称",
	"x": "x",
	"y": "y",
	"z": "海拔",
	"holeDepth": "孔深",
	"ph": "PH",
	"waterTemperature": "水温",
	"waterInflow": "涌水量",
	"trepanning": "开孔/出露层位",
	"waterOutlet": "出水段",
	"dissolvedSolids": "溶解性总固体",
	"co2": "二氧化碳",
	"hydrothion": "总硫化氢",
	"hsio": "偏硅酸",
	"hbo2": "偏硼酸",
	"br2": "溴",
	"i2": "碘",
	"fe": "总铁",
	"asa": "砷",
	"rn": "氡",
	"hydrochemicalType": "水化学类型",
	"pointCategory": "温泉类型",
	"createTime": "创建时间"
};
//温泉点测试数据
var visualpoint = [{
		"latlng": [1558.156759601893, 6416.941167692173],
		"properties": {
			"dissolvedSolids": 10001,
			"co2": Math.floor(Math.random()),
			"hydrothion": Math.floor(Math.random()),
			"hsio": Math.floor(Math.random()),
			"hbo2": Math.floor(Math.random()),
			"br2": Math.floor(Math.random()),
			"i2": Math.floor(Math.random()),
			"fe": Math.floor(Math.random()),
			"asa": Math.floor(Math.random()),
			"rn": Math.floor(Math.random()),
			"name": "矿泉水",
			"codeNumber": 100001
		}
	},
	{
		"latlng": [1337.9366231342924, 5688.141157241805],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": 501,
			"hydrothion": Math.floor(Math.random()),
			"hsio": Math.floor(Math.random()),
			"hbo2": Math.floor(Math.random()),
			"br2": Math.floor(Math.random()),
			"i2": Math.floor(Math.random()),
			"fe": Math.floor(Math.random()),
			"asa": Math.floor(Math.random()),
			"rn": Math.floor(Math.random()),
			"name": "碳酸水",
			"codeNumber": 100002
		}
	},
	{
		"latlng": [1583.6585023737064, 5853.381356395495],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": Math.floor(Math.random()),
			"hydrothion": 3,
			"hsio": Math.floor(Math.random()),
			"hbo2": Math.floor(Math.random()),
			"br2": Math.floor(Math.random()),
			"i2": Math.floor(Math.random()),
			"fe": Math.floor(Math.random()),
			"asa": Math.floor(Math.random()),
			"rn": Math.floor(Math.random()),
			"name": "硫化氢水",
			"codeNumber": 100003
		}
	},
	{
		"latlng": [1069.1403459809264, 5865.449171819021],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": Math.floor(Math.random()),
			"hydrothion": Math.floor(Math.random()),
			"hsio": 51,
			"hbo2": Math.floor(Math.random()),
			"br2": Math.floor(Math.random()),
			"i2": Math.floor(Math.random()),
			"fe": Math.floor(Math.random()),
			"asa": Math.floor(Math.random()),
			"rn": Math.floor(Math.random()),
			"name": "硅酸水",
			"codeNumber": 100004
		}
	},
	{
		"latlng": [1327.0387774299284, 5879.417466329692],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": Math.floor(Math.random()),
			"hydrothion": Math.floor(Math.random()),
			"hsio": Math.floor(Math.random()),
			"hbo2": 36,
			"br2": Math.floor(Math.random()),
			"i2": Math.floor(Math.random()),
			"fe": Math.floor(Math.random()),
			"asa": Math.floor(Math.random()),
			"rn": Math.floor(Math.random()),
			"name": "硼酸水",
			"codeNumber": 100005
		}
	},
	{
		"latlng": [1343.7663418306565, 5859.614069646941],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": Math.floor(Math.random()),
			"hydrothion": Math.floor(Math.random()),
			"hsio": Math.floor(Math.random()),
			"hbo2": Math.floor(Math.random()),
			"br2": 6,
			"i2": Math.floor(Math.random()),
			"fe": Math.floor(Math.random()),
			"asa": Math.floor(Math.random()),
			"rn": Math.floor(Math.random()),
			"name": "碘水",
			"codeNumber": 100006
		}
	},
	{
		"latlng": [1185.4862450271612, 5923.353686311734],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": Math.floor(Math.random()),
			"hydrothion": Math.floor(Math.random()),
			"hsio": Math.floor(Math.random()),
			"hbo2": Math.floor(Math.random()),
			"br2": Math.floor(Math.random()),
			"i2": 11,
			"fe": Math.floor(Math.random()),
			"asa": Math.floor(Math.random()),
			"rn": Math.floor(Math.random()),
			"name": "铁水",
			"codeNumber": 100007
		}
	},
	{
		"latlng": [1646.2309305244903, 5877.6395040083835],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": Math.floor(Math.random()),
			"hydrothion": Math.floor(Math.random()),
			"hsio": Math.floor(Math.random()),
			"hbo2": Math.floor(Math.random()),
			"br2": Math.floor(Math.random()),
			"i2": Math.floor(Math.random()),
			"fe": 0.8,
			"asa": Math.floor(Math.random()),
			"rn": Math.floor(Math.random()),
			"name": "砷水",
			"codeNumber": 100008
		}
	},
	{
		"latlng": [1559.2078926797144, 5881.841633252836],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": Math.floor(Math.random()),
			"hydrothion": Math.floor(Math.random()),
			"hsio": Math.floor(Math.random()),
			"hbo2": Math.floor(Math.random()),
			"br2": Math.floor(Math.random()),
			"i2": Math.floor(Math.random()),
			"fe": Math.floor(Math.random()),
			"asa": 111,
			"rn": Math.floor(Math.random()),
			"name": "氡水",
			"codeNumber": 100009
		}
	},
	{
		"latlng": [1458.580120648891, 5922.612347354173],
		"properties": {
			"dissolvedSolids": Math.floor(Math.random()),
			"co2": Math.floor(Math.random()),
			"hydrothion": Math.floor(Math.random()),
			"hsio": Math.floor(Math.random()),
			"hbo2": Math.floor(Math.random()),
			"br2": Math.floor(Math.random()),
			"i2": Math.floor(Math.random()),
			"fe": Math.floor(Math.random()),
			"asa": Math.floor(Math.random()),
			"rn": 26,
			"name": "溴水",
			"codeNumber": 100010
		}
	},
];
