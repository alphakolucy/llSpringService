proj4.defs("EPSG:2385",
    "+proj=tmerc +lat_0=0 +lon_0=120 +k=1 +x_0=500000 +y_0=0 +a=6378140 +b=6356755.288157528 +units=m +no_defs");
// proj4.defs("EPSG:2385",
// 	"+proj=tmerc +lat_0=0 +lon_0=120 +k=1 +x_0=1500000 +y_0=-3000000 +a=6378140 +b=6356755.288157528 +units=m +no_defs"
// );
var mapcenter = [0.05352918955315693, 115.52501678466798];
var mapzoom = 17;

var map = L.map('map', {
    center: mapcenter,
    zoom: mapzoom,
    minZoom: 15,
    maxZoom: 21,
    // crs: L.CRS.EPSG4326,
    zoomControl: false,
    attributionControl: false,
});

var curpointjson = {};
var allpointdata = {};
var getpointlist;
var showtype = 1; //1 直接点地图点上面显示 2 弹出详情框


var provincelayer = new L.layerGroup();
var structurelayer = new L.layerGroup();
var residentiallayer = new L.layerGroup();
var hotspringslayer = new L.layerGroup();
var riverslayer = new L.layerGroup();
var faultlayer = new L.layerGroup();
var trafficlayer = new L.layerGroup();
var picturelayer = new L.layerGroup();
var highspeedlayer = new L.layerGroup();
var citynamelayer = new L.layerGroup();


// L.tileLayer(
// 	'https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
// 		maxZoom: 18,
// 		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
// 			'<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
// 			'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
// 		id: 'mapbox.streets'
// 	}).addTo(map);
picturelayer.addTo(map);
provincelayer.addTo(map);
citynamelayer.addTo(map);

// riverslayer.addTo(map);
// structurelayer.addTo(map);
// residentiallayer.addTo(map);
// faultlayer.addTo(map);
trafficlayer.addTo(map);
highspeedlayer.addTo(map);

hotspringslayer.addTo(map);
var printer = L.easyPrint({
    tileLayer: null,
    sizeModes: ['Current', 'A4Landscape', 'A4Portrait'],
    filename: 'myMap',
    exportOnly: true,
    tileWait: 200,
    hideControlContainer: true,
    hidden: true
}).addTo(map);

function readdpoint(lat, lng, properties) {
    var relat = lat / pointrotiox;
    var relng = lng / pointrotioy;
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
            coordinates: [relng, relat]
        },
        crs: {
            type: "name",
            properties: {
                name: "EPSG:2385"
            }
        },
        properties: properties
    };
    var pointiconUrl = 'img/default.png';
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
    }).addTo(hotspringslayer);
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
                    if (waterstanard.hasOwnProperty(key)) {
                        if (pointproperties[key] > waterstanard[key]) {
                            popuphtml.push("<span style=\"color:#FF0000\">" + showproperties[key] + ":" + pointproperties[key] +
                                "</span>");
                        } else {
                            popuphtml.push("<span>" + showproperties[key] + ":" + pointproperties[key] + "</span>");
                        }
                    } else {
                        popuphtml.push("<span>" + showproperties[key] + ":" + pointproperties[key] + "</span>");
                    }
                }
            });
            var popup = L.popup()
                .setLatLng(e.latlng)
                .setContent(popuphtml.join("</br>"))
                .openOn(hotspringslayer);
        }
    }).addTo(hotspringslayer);
}



layui.use(['layer', 'jquery', 'form'], function() {
    var layer = layui.layer
    var $ = layui.jquery;
    var form = layui.form;

    getpointlist = function() {
        hotspringslayer.clearLayers();
        //获取已存在的温泉点
        $.ajax({
            type: "GET",
            url: apiUrl + "/springPoint",
            dataType: "json",
            success: function(data) {
                allpointdata = data;
                $.each(data, function(i, item) {
                    readdpoint(item.x, item.y, item);
                })
            },
            errorfunction(data) {
                console.log(data);
            }
        });
    };
    getpointlist();

    //加载图框
    $.getJSON("jsondata/pictureframe.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#BEBEBE',
                    "weight": 0.5,
                    "opacity": 0.5
                }
            }).addTo(picturelayer);
        });
    });
    //加载图名
    $.getJSON("jsondata/picturename.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#000000',
                    "weight": 0.5,
                    "opacity": 1
                }
            }).addTo(picturelayer);
        });
    });


    //加载县界
    $.getJSON("jsondata/county.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        var provices = []
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": "#BEBEBE",
                    "weight": 1,
                    "opacity": 0.5
                }
            }).addTo(provincelayer);
        });
    });



    //加载市界
    $.getJSON("jsondata/city.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        var provices = []
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": "#BEBEBE",
                    "weight": 1.5,
                    "opacity": 1,
                    "dashArray": '5',
                    "lineCap": 'butt',
                    "lineJoin": 'miter',
                }
            }).addTo(provincelayer);
        });
    });
    //加载市名称
    $.getJSON("jsondata/cityname.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        var provices = []
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var myIcontext = L.divIcon({
                html: item.properties.name,
                className: 'my-div-city-name',
            });
            L.Proj.geoJson(item, {
                pointToLayer: function(feature, latlng) {
                    return L.marker(latlng, {
                        icon: myIcontext,
                    });
                },
            }).addTo(citynamelayer);
        });
    });

    //加载省界
    $.getJSON("jsondata/province.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        var provices = []
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };

            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": "#BEBEBE", //边界颜色
                    "weight": 2,
                    "opacity": 0.75,
                    "dashArray": '5',
                    "lineCap": 'butt',
                    "lineJoin": 'miter',
                }
            }).addTo(provincelayer);
        });
    });

    //加载河流
    $.getJSON("jsondata/rivers-line.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#B2FFFF',
                    "weight": 0.3,
                    "opacity": 1,
                }
            }).addTo(riverslayer);

        });
    });

    //加载活性断层
    $.getJSON("jsondata/fault.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#FF0000',
                    "weight": 0.5,
                    "opacity": 1
                }
            }).addTo(faultlayer);
        });
    });
    //加载一般断层
    $.getJSON("jsondata/generalfault.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#FF0000',
                    "weight": 0.3,
                    "opacity": 1
                }
            }).addTo(faultlayer);
        });
    });
    //加载交通
    $.getJSON("jsondata/traffic.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#FFD898',
                    "weight": 0.3,
                    "opacity": 1
                }
            }).addTo(trafficlayer);
        });
    });
    //加载高速
    $.getJSON("jsondata/highspeed.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#FFD832',
                    "weight": 0.3,
                    "opacity": 1
                }
            }).addTo(highspeedlayer);
        });
    });
    //加载构造
    $.getJSON("jsondata/structure.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#0000FF',
                    "weight": 0.3,
                    "opacity": 1
                }
            }).addTo(structurelayer);
        });
    });
    //加载主要构造
    $.getJSON("jsondata/mainstructure.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var curprovice = L.Proj.geoJson(item, {
                style: {
                    "color": '#99FF7F',
                    "weight": 0.3,
                    "opacity": 1
                }
            }).addTo(structurelayer);
        });
    });
    //加载乡镇点
    $.getJSON("jsondata/township.json", "", function(data) {
        //each循环 使用$.each方法遍历返回的数据date
        var provices = []
        $.each(data.features, function(i, item) {
            item["crs"] = {
                type: "name",
                properties: {
                    name: "EPSG:2385"
                }
            };
            var myIcontext = L.divIcon({
                html: item.properties.pname,
                className: 'my-div-icon-name',
                // iconSize: 30,
            });
            var myIcon = L.divIcon({
                className: 'my-div-icon-namepoint',
                html: '·'
            });
            // L.Proj.geoJson(item, {
            // 	pointToLayer: function(feature, latlng) {
            // 		// return L.circleMarker(latlng, geojsonMarkerOptions);
            // 		return L.marker(latlng, {
            // 			icon: myIcontext,
            // 		});
            // 	},
            // }).addTo(residentials);
            L.Proj.geoJson(item, {
                pointToLayer: function(feature, latlng) {
                    return L.marker(latlng, {
                        icon: myIcon,
                    });
                },
            }).addTo(residentiallayer);
        });
    });


    //图层切换监听
    var changesfeature = ['structure', 'fault', 'residential', 'rivers', 'traffic', 'highspeed'];
    form.on('checkbox(layers)', function(data) {
        var layername = $(data.elem).attr('name');
        var layercheck = data.elem.checked;
        var reallayername = layername + 'layer';
        if (layercheck) {
            switch (layername) {
                case 'layersall':
                {
                    $.each(changesfeature, function(index, item) {
                        $("#" + item).prop("checked", true);
                        eval(item + 'layer').addTo(map);
                    });
                    break;
                }
                default:
                {
                    $("#layersall").prop("checked", true);
                    eval(reallayername).addTo(map);
                    break;
                }
            }
        } else {
            switch (layername) {
                case 'layersall':
                {
                    $.each(changesfeature, function(index, item) {
                        $("#" + item).prop("checked", false);
                        map.removeLayer(eval(item + 'layer'));
                    });
                    break;
                }
                default:
                {
                    var isfull = true;
                    $.each(changesfeature, function(index, item) {
                        if ($("#" + item)[0].checked) {
                            isfull = false;
                            return false;
                        }
                    });
                    if (isfull) {
                        $("#layersall").prop("checked", false);
                    }
                    map.removeLayer(eval(reallayername));
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
    // $('#searchpoint').click();
});



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
        case 3:
        {
            if (map.getZoom() == mapzoom) {
                layer.msg('地图开始导出中');
                printer.printMap('CurrentSize', '导出地图');
                // var modeToUse = L.control.browserPrint.mode.auto();
                // map.printControl.print(modeToUse);
            } else {
                map.setView(mapcenter);
                map.setZoom(mapzoom);
                var interval = setInterval(function() {
                    layer.msg('地图开始导出中');
                    printer.printMap('CurrentSize', '导出地图');
                    // var modeToUse = L.control.browserPrint.mode.auto();
                    // map.printControl.print(modeToUse);

                    clearInterval(interval);
                }, 200);
            }
            break;
        }
    }
}

function tableToExcel(tabletitles, jsonData) {
    //列标题，逗号隔开，每一个逗号就是隔开一个单元格
    let str = "<tr>";
    for (let item in tabletitles) {
        str += `<td>${ tabletitles[item] + '\t'}</td>`;
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
