proj4.defs("urn:ogc:def:crs:EPSG::2385",
	"+proj=tmerc +lat_0=0 +lon_0=120 +k=1 +x_0=1500000 +y_0=-3000000 +a=6378140 +b=6356755.288157528 +units=m +no_defs"
);
var mapcenter = [26.3715, 105.086682];
var mapzoom = 16;

var curpointjson = {};
var allpointdata = {};
var getpointlist;
var showtype = 1; //1 直接点地图点上面显示 2 弹出详情框

var structures = new L.layerGroup();
var slips = new L.layerGroup();
var residentials = new L.layerGroup();
var hotsprings = new L.layerGroup();


var map = L.map('map', {
	center: mapcenter,
	zoom: mapzoom,
	minZoom: 14,
	maxZoom: 26,
	crs: L.CRS.EPSG4326,
	zoomControl: false,
	attributionControl: false,
});

structures.addTo(map);
slips.addTo(map);
residentials.addTo(map);
hotsprings.addTo(map);


function readdpoint(latlng, properties) {
	console.log(latlng);
	latlng.x
	var geojsonMarkerOptions = {
		radius: 1,
		fillColor: "#ff7800",
		color: "blue",
		weight: 1,
		opacity: 1,
		fillOpacity: 0.6
	};
	var geojson = {
		type: "Feature",
		geometry: {
			type: "Point",
			coordinates: latlng
		},
		crs: {
			type: "name",
			properties: {
				name: "urn:ogc:def:crs:EPSG::2385"
			}
		},
		properties: properties
	};
	var pointiconUrl = 'img/hotspring.png';
	if (properties.pointCategory == 30001) {
		pointiconUrl = 'img/hotspring.png'; //天然温泉井
	} else if (properties.pointCategory == 30002) {
		pointiconUrl = 'img/lanhotwell.png'; //地热井
	} else if (properties.pointCategory == 30003) {
		pointiconUrl = 'img/build-drill.png'; //施工中热矿水转孔
	} else if (properties.pointCategory == -30001) {
		pointiconUrl = 'img/hotspring-gray.png'; //不达标温泉
	} else if (properties.pointCategory == -30002) {
		pointiconUrl = 'img/lanhotwell-gray.png'; //不达标地热
	}else if (properties.pointCategory == -30003) {
        pointiconUrl = 'img/default.png'; //不达标地热
    }
	var myIcon = L.icon({
		iconUrl: pointiconUrl,
		iconSize: [20, 20],
		iconAnchor: [10, 10],
		popupAnchor: [10, 10],
		// shadowUrl: 'my-icon-shadow.png',
		// shadowSize: [68, 95],
		// shadowAnchor: [22, 94]
	});
	var myIcontext = L.divIcon({
		html: properties.codeNumber,
		className: 'my-div-icon',
		iconSize: 30,
	});

	L.Proj.geoJson(geojson, {
		pointToLayer: function(feature, latlng) {
			return L.marker(latlng, {
				icon: myIcontext,
			});
		},
	}).addTo(hotsprings);
	L.Proj.geoJson(geojson, {
		pointToLayer: function(feature, latlng) {
			return L.marker(latlng, {
				icon: myIcon,
			});
		},
	}).on('click', function(e) {
		var pointproperties = e.layer.feature.properties; //当前点击的物体的名称
		if (showtype == 2) {
			curpointjson = pointproperties;
			layer.open({
				type: 2,
				title: "温泉点详情",
				area: ['1000px', '800px'],
				shadeClose: true, //点击遮罩关闭
				content: 'html/pointdetail.html'
			});
		} else if (showtype == 1) {
			var popuphtml = [];
			Object.keys(pointproperties).forEach(function(key) {
				if (showproperties.hasOwnProperty(key)) {
					popuphtml.push(showproperties[key] + ":" + pointproperties[key]);
				}
			});
			var popup = L.popup()
				.setLatLng(e.latlng)
				.setContent(popuphtml.join("</br>"))
				.openOn(map);
		}
	}).addTo(hotsprings);
}

function provicestyle() {
	var r = Math.floor(Math.random() * 256);
	var g = Math.floor(Math.random() * 256);
	var b = Math.floor(Math.random() * 256);
	return {
		"color": "#000000", //边界颜色
		"fillColor": "#FFFFFF", //县面颜色
		"weight": 0.5,
		"opacity": 0.6,
		"dashArray": '5',
		"lineCap": 'butt',
		"lineJoin": 'miter',
	}
}

function func_map(type) {
	switch (type) {
		case 0:
			{
				map.setView(mapcenter);
				map.setZoom(mapzoom);
				break;
			}
		case 1:
			{
				map.zoomIn();
				break;
			}
		case 2:
			{
				map.zoomOut();
				break;
			}
	}
}

function tableToExcel(tabletitles, jsonData) {
	//列标题，逗号隔开，每一个逗号就是隔开一个单元格
	let str = "<tr>";
	for (let item in tabletitles) {
		str +=`<td>${ tabletitles[item] + '\t'}</td>`;
	}
	str += '</tr>';
	// `姓名,电话,邮箱\n`;
	//增加\t为了不让表格显示科学计数法或者其他格式
	for (let i = 0; i < jsonData.length; i++) {
		str += '<tr>';
		for (let item in tabletitles) {
			if (jsonData[i].hasOwnProperty(item)) {
				if (item == "pointCategory") {
					switch (jsonData[i][item]) {
						case 30001:
							str += "<td>天然温泉\t</td>";
							break;
						case 30002:
							str += "<td>地热井\t</td>";
							break;
						case 30003:
							str += "<td>施工中热矿水转孔\t</td>";
							break;
						case -30001:
							str += "<td>不达标温泉\t</td>";
							break;
						case -30002:
							str += "<td>不达标地热\t</td>";
							break;
						default:
							str += "<td>未分类\t</td>";
							break;
					}
				} else {
					str += `<td>${jsonData[i][item] + '\t'}</td>`;
				}
			}
		}
		str += '</tr>';
	}
	//Worksheet名
	let worksheet = '温泉点数据'
	let uri = 'data:application/vnd.ms-excel;base64,';

	//下载的表格模板数据
	let template =
		`<html xmlns:o="urn:schemas-microsoft-com:office:office" 
	      xmlns:x="urn:schemas-microsoft-com:office:excel" 
	      xmlns="http://www.w3.org/TR/REC-html40">
	      <head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>
	        <x:Name>${worksheet}</x:Name>
	        <x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet>
	        </x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]-->
	        </head><body><table>${str}</table></body></html>`;
	//下载模板
	let link = document.createElement("a");
	link.href = uri + base64(template);
	//对下载的文件命名
	link.download = "温泉点数据.xls";
	document.body.appendChild(link);
	link.click();
	document.body.removeChild(link);
	
}
//输出base64编码
function base64(s) {
	return window.btoa(unescape(encodeURIComponent(s)))
}

function tableToExcelcsv(tabletitles, jsonData) {
	//列标题，逗号隔开，每一个逗号就是隔开一个单元格
	tabletitletext = [];
	for (let item in tabletitles) {
		tabletitletext.push(tabletitles[item])
	}
	let str = tabletitletext.join(',') + "\n";
	// `姓名,电话,邮箱\n`;
	//增加\t为了不让表格显示科学计数法或者其他格式
	for (let i = 0; i < jsonData.length; i++) {
		for (let item in tabletitles) {
			if (jsonData[i].hasOwnProperty(item)) {
				if (item == "pointCategory") {
					switch (jsonData[i][item]) {
						case 30001:
							str += "天然温泉\t,";
							break;
						case 30002:
							str += "地热井\t,";
							break;
						case 30003:
							str += "施工中热矿水转孔\t,";
							break;
						case -30001:
							str += "不达标温泉\t,";
							break;
						case -30002:
							str += "不达标地热\t,";
							break;
						default:
							str += "未分类\t,";
							break;
					}
				} else {
					str += `${jsonData[i][item] + '\t'},`;
				}
			}
		}
		str += '\n';
	}
	//encodeURIComponent解决中文乱码
	let uri = 'data:text/csv;charset=utf-8,\ufeff' + encodeURIComponent(str);
	//通过创建a标签实现
	let link = document.createElement("a");
	link.href = uri;
	//对下载的文件命名
	link.download = "温泉点数据.csv";
	document.body.appendChild(link);
	link.click();
	document.body.removeChild(link);
}
var exportdata = function() {
	tableToExcel(pointtabletitles, allpointdata);
}

layui.use(['layer', 'jquery', 'form'], function() {
	var layer = layui.layer
	var $ = layui.jquery;
	var form = layui.form;

	getpointlist = function() {
		hotsprings.clearLayers();
		//获取已存在的温泉点
		$.ajax({
			type: "GET",
			url: apiUrl + "/springPoint",
			dataType: "json",
			success: function(data) {
				allpointdata = data;
				$.each(data, function(i, item) {
					readdpoint([item.y / pointrotiox, item.x / pointrotioy], item);
				})
			},
			errorfunction(data) {
				console.log(data);
			}
		});
	};

	getpointlist();





	//加载县市
	$.getJSON("jsondata/provice.json", "", function(data) {
		//each循环 使用$.each方法遍历返回的数据date
		var provices = []
		$.each(data.features, function(i, item) {
			item["crs"] = {
				type: "name",
				properties: {
					name: "urn:ogc:def:crs:EPSG::2385"
				}
			};
			var curprovice = L.Proj.geoJson(item, {
				style: provicestyle()
			}).addTo(map);
		});
	});


	//加载省界
	$.getJSON("jsondata/proviceline.json", "", function(data) {
		//each循环 使用$.each方法遍历返回的数据date
		var provices = []
		$.each(data.features, function(i, item) {
			item["crs"] = {
				type: "name",
				properties: {
					name: "urn:ogc:def:crs:EPSG::2385"
				}
			};
			var curprovice = L.Proj.geoJson(item, {
				style: {
					"color": "#5F5F5F",
					"weight": 1,
					"opacity": 1,
					"dashArray": '',
					"lineCap": 'butt',
					"lineJoin": 'miter',
				}
			}).addTo(map);
		});
	});

	//加载构造1 穹隆背斜
	$.getJSON("jsondata/structureone.json", "", function(data) {
		//each循环 使用$.each方法遍历返回的数据date
		$.each(data.features, function(i, item) {
			item["crs"] = {
				type: "name",
				properties: {
					name: "urn:ogc:def:crs:EPSG::2385"
				}
			};
			var curprovice = L.Proj.geoJson(item, {
				style: {
					"color": '#00FF00',
					"weight": 0.3,
					"opacity": 1,
					"dashArray": '',
					"lineCap": 'butt',
					"lineJoin": 'miter',
				}
			}).addTo(structures);

		});
	});

	//加载构造2  穹隆背斜
	$.getJSON("jsondata/structuretwo.json", "", function(data) {
		//each循环 使用$.each方法遍历返回的数据date
		$.each(data.features, function(i, item) {
			item["crs"] = {
				type: "name",
				properties: {
					name: "urn:ogc:def:crs:EPSG::2385"
				}
			};
			var curprovice = L.Proj.geoJson(item, {
				style: {
					"color": '#00FF00',
					"weight": 0.3,
					"opacity": 1
				}
			}).addTo(structures);
		});
	});

	//普通构造 盆状向斜
	$.getJSON("jsondata/normalstructure.json", "", function(data) {
		//each循环 使用$.each方法遍历返回的数据date
		$.each(data.features, function(i, item) {
			item["crs"] = {
				type: "name",
				properties: {
					name: "urn:ogc:def:crs:EPSG::2385"
				}
			};
			var curprovice = L.Proj.geoJson(item, {
				style: {
					"color": '#00FF00',
					"weight": 0.3,
					"opacity": 1
				}
			}).addTo(structures);
		});
	});

	//普通断层  断层
	$.getJSON("jsondata/normalslip.json", "", function(data) {
		//each循环 使用$.each方法遍历返回的数据date
		$.each(data.features, function(i, item) {
			item["crs"] = {
				type: "name",
				properties: {
					name: "urn:ogc:def:crs:EPSG::2385"
				}
			};
			var curprovice = L.Proj.geoJson(item, {
				style: {
					"color": '#FF0000',
					"weight": 0.3,
					"opacity": 1
				}
			}).addTo(slips);
		});
	});

	//活性断层 挽进期活动断层
	$.getJSON("jsondata/activeslip.json", "", function(data) {
		//each循环 使用$.each方法遍历返回的数据date
		var provices = []
		$.each(data.features, function(i, item) {
			item["crs"] = {
				type: "name",
				properties: {
					name: "urn:ogc:def:crs:EPSG::2385"
				}
			};
			var curprovice = L.Proj.geoJson(item, {
				style: {
					"color": '#FF0000',
					"weight": 0.3,
					"opacity": 1
				}
			}).addTo(slips);
		});
	});

	//居民点
	$.getJSON("jsondata/residential.json", "", function(data) {
		//each循环 使用$.each方法遍历返回的数据date
		var provices = []
		$.each(data.features, function(i, item) {
			item["crs"] = {
				type: "name",
				properties: {
					name: "urn:ogc:def:crs:EPSG::2385"
				}
			};
			// var myIcon = L.icon({
			// 	iconUrl: 'img/grovement.png',
			// 	iconSize: [20, 20],
			// 	iconAnchor: [10, 10],
			// 	popupAnchor: [10, 10],
			// 	// shadowUrl: 'my-icon-shadow.png',
			// 	// shadowSize: [68, 95],
			// 	// shadowAnchor: [22, 94]
			// });
			var myIcontext = L.divIcon({
				html: item.properties.pname,
				className: 'my-div-icon-name',
				// iconSize: 30,
			});
			var myIcon = L.divIcon({
				className: 'my-div-icon-namepoint',
				html: '·'
			});
			L.Proj.geoJson(item, {
				pointToLayer: function(feature, latlng) {
					// return L.circleMarker(latlng, geojsonMarkerOptions);
					return L.marker(latlng, {
						icon: myIcontext,
					});
				},
			}).addTo(residentials);
			L.Proj.geoJson(item, {
				pointToLayer: function(feature, latlng) {
					// return L.circleMarker(latlng, geojsonMarkerOptions);
					return L.marker(latlng, {
						icon: myIcon,
					});
				},
			}).addTo(residentials);
			// on('click', function(e) {
			// 	var a = e.layer.feature.properties; //当前点击的物体的名称
			// 	if (a) {
			// 		var keys = [];
			// 		Object.keys(a).forEach(function(key) {
			// 			keys.push(key + ":" + a[key]);
			// 		});
			// 		var popup = L.popup()
			// 			.setLatLng(e.latlng)
			// 			.setContent(keys.join('<br>'))
			// 			.openOn(map);
			// 		return false;
			// 	}
			// });
		});
	});

	//图层切换监听
	form.on('checkbox(layers)', function(data) {
		var layername = $(data.elem).attr('name');
		var layercheck = data.elem.checked;
		if (layercheck) {
			switch (layername) {
				case 'layersall':
					{
						$("#structure").prop("checked", true);
						$("#slip").prop("checked", true);
						$("#residential").prop("checked", true);
						structures.addTo(map);
						slips.addTo(map);
						residentials.addTo(map);
						break;
					}
				case 'structure':
					{
						var slipcheck = $("#slip")[0].checked;
						var residentialcheck = $("#residential")[0].checked;
						if (slipcheck && residentialcheck) {

						} else {
							$("#layersall").prop("checked", true);
						}
						structures.addTo(map);
						break;
					}
				case 'slip':
					{
						var structurecheck = $("#structure")[0].checked;
						var residentialcheck = $("#residential")[0].checked;
						if (structurecheck && residentialcheck) {

						} else {
							$("#layersall").prop("checked", true);
						}
						slips.addTo(map);
						break;
					}
				case 'residential':
					{
						var structurecheck = $("#structure")[0].checked;
						var slipcheck = $("#slip")[0].checked;
						if (structurecheck && residentialcheck) {

						} else {
							$("#layersall").prop("checked", true);
						}
						residentials.addTo(map);
						break;
					}
				default:
					{
						break;
					}
			}
		} else {
			switch (layername) {
				case 'layersall':
					{
						$("#structure").prop("checked", false);
						$("#slip").prop("checked", false);
						$("#residential").prop("checked", false);
						map.removeLayer(structures);
						map.removeLayer(slips);
						map.removeLayer(residentials);
						break;
					}
				case 'structure':
					{
						var slipcheck = $("#slip")[0].checked;
						var residentialcheck = $("#residential")[0].checked;
						if (slipcheck || residentialcheck) {

						} else {
							$("#layersall").prop("checked", false);
						}
						map.removeLayer(structures);
						break;
					}
				case 'slip':
					{
						var structurecheck = $("#structure")[0].checked;
						var residentialcheck = $("#residential")[0].checked;
						if (structurecheck || residentialcheck) {

						} else {
							$("#layersall").prop("checked", false);
						}
						map.removeLayer(slips);
						break;
					}
				case 'residential':
					{
						var structurecheck = $("#structure")[0].checked;
						var slipcheck = $("#slip")[0].checked;
						if (structurecheck || residentialcheck) {

						} else {
							$("#layersall").prop("checked", false);
						}
						map.removeLayer(residentials);
						break;
					}
				default:
					{
						break;
					}
			}
		}
		form.render('checkbox');
	});

	//水质切换监听
	form.on('checkbox(waterquality)', function(data) {
		var layername = $(data.elem).attr('name');
		var layercheck = data.elem.checked;
		if (layername == "waterqualityall") {
			if (layercheck) {
				Object.keys(waterstanard).forEach(function(key) {
					$("#" + key).prop("checked", true);
				});
				hotsprings.eachLayer(function(layer) {
					map.addLayer(layer);
				});
			} else {
				Object.keys(waterstanard).forEach(function(key) {
					$("#" + key).prop("checked", false);
				});
				hotsprings.eachLayer(function(layer) {
					map.removeLayer(layer);
				});
			}

		} else {
			if (layercheck) {
				var layerkeys = false;
				Object.keys(waterstanard).forEach(function(key) {
					if ($("#" + key)[0].checked) {
						layerkeys = true;
					}
				});
				if (layerkeys) {
					$("#waterqualityall").prop("checked", true);
				}
				hotsprings.eachLayer(function(layer) {
					var properties = layer._layers[layer._leaflet_id - 1].feature.properties;
					if (properties[layername] > waterstanard[layername]) {
						map.addLayer(layer);
					}
				});
			} else {
				var layerkeys = false;
				Object.keys(waterstanard).forEach(function(key) {
					if (key != layername) {
						if ($("#" + key)[0].checked) {
							layerkeys = true;
						}
					}
				});
				if (!layerkeys) {
					$("#waterqualityall").prop("checked", false);
				}
				hotsprings.eachLayer(function(layer) {
					var properties = layer._layers[layer._leaflet_id - 1].feature.properties;
					if (properties[layername] > waterstanard[layername]) {
						map.removeLayer(layer);
					}
				});
			}
		}
		form.render('checkbox');
	});

	$("#clickmenu").on('click', function() {
		$("#menuitem").toggle();
	});

	$('#springPoint').on('click', function() {
		layer.open({
			type: 2,
			title: "添加温泉点",
			area: ['1000px', '800px'],
			shadeClose: true, //点击遮罩关闭
			content: 'html/pointadd.html'
		});
	});

	// $('#springPoint').click();
	$('#searchpoint').on('click', function() {
		var searchpoint = layer.open({
			type: 2,
			title: "基础数据列表",
			area: ['1000px', '600px'],
			shadeClose: true, //点击遮罩关闭
			content: 'html/pointlist.html',
			success: function(layero, index) {
				// console.log(layero);
			},
		});
		layer.full(searchpoint);
	});
    $('#springPicHis').on('click', function() {
        var searchpoint = layer.open({
            type: 2,
            title: "温泉点上传图片记录",
            area: ['1000px', '600px'],
            shadeClose: true, //点击遮罩关闭
            content: 'html/pointpiclist.html',
            success: function(layero, index) {
            },
        });
        layer.full(searchpoint);
    });
	// $('#searchpoint').click();
});




// const resolutions = [ // 所有的分辨率复制到这里
// 	86.03597249425798, 43.01798624712899,
// 	21.508993123564494, 10.754496561782247,
// 	5.3772482808911235, 2.6886241404455617,
// 	1.3443120702227809, 0.6721560351113904,
// 	0.3360780175556952, 0.1680390087778476,
// 	0.0840195043889238, 0.0420097521944619,
// 	0.02100487609723095, 0.010502438048615476,
// 	0.005251219024307738, 0.002625609512153869
// ];

// const CRS_4610 = new L.Proj.CRS('EPSG:2385',
// 	'+proj=tmerc +lat_0=0 +lon_0=120 +k=1 +x_0=500000 +y_0=0 +a=6378140 +b=6356755.288157528 +units=m +no_defs', {
// 		resolutions: resolutions
// 	}
// );
// .setView([26.655132, 106.637155], 7);

// var shpfile = new L.Shapefile('hotspring.zip', {
// 	onEachFeature: function(feature, layer) {
// 		if (feature.properties) {
// 			console.log(feature);
// 			layer.bindPopup(Object.keys(feature.properties).map(function(k) {
// 				return k + ": " + feature.properties[k];
// 			}).join("<br />"), {
// 				maxHeight: 200
// 			});
// 		}
// 	}
// });
// shpfile.addTo(map);
// shpfile.once("data:loaded", function() {
// 	console.log("finished loaded shapefile");
// });

// bindPopup(function(layer) {
// 	// console.log(this._latlng);
// 	if (layer.feature.properties) {
// 		var keys = [];
// 		Object.keys(layer.feature.properties).forEach(function(key) {
// 			keys.push(layer.feature.properties[key]);
// 		});
// 		var popup = L.popup()
// 			.setLatLng(this._latlng)
// 			.setContent(keys.join(','))
// 			.openOn(map);
// 		return false;
// 	}
// }).addTo(map);
