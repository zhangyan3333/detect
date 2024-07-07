import AMapLoader from '@amap/amap-jsapi-loader'

import marker_default from '@/assets/images/marker_default.png';

let map = null;
let icons = null;
let iconOffsets = null;
let colors = null;

let points = [];
let regions = [];
let lines = [];

let locationPoint = undefined;
let dynamicLine = undefined;

let defaultLngLatAlt = undefined;

let altitudeAmend = 0;
let isAmendAltitude = false;

export const mapUtil = {};

/**
 * 初始化地图
 * @param container 地图容器id
 * @param initParams 初始化参数
 * @param iconPaths 图表路径集
 * @param colorStrings 颜色集合
 * @param onComplete 初始化完成回调函数
 */
mapUtil.initialize = function (container, initParams, iconInfos, colorStrings, onComplete) {
    console.log('开始初始化地图');
    AMapLoader.load({
        key: "e4f2f181fdc3cce18f073f01caf791b2",               // 申请好的Web端开发者Key，首次调用 load 时必填
        version: "2.0",                                        // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
        plugins: ['AMap.Scale', 'AMap.MapType', 'AMap.HawkEye'], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
    }).then((AMap) => {
        if (!initParams) {
            initParams = {
                zoom: 11,           //初始化地图默认层级
            };
        }

        //初始化地图
        map = new AMap.Map(container, initParams);
        map.addControl(new AMap.Scale());
        map.addControl(new AMap.MapType());
        map.addControl(new AMap.HawkEye({opened: true}));

        //绑定地图加载完成事件
        map.on("complete", onComplete ? onComplete : function () {
            console.log('初始化地图完成！！');
        });

        //初始化图标
        mapUtil.setIcons(iconInfos === undefined ? [{path: marker_default, width: 19, height: 33}] : iconInfos);

        //初始化颜色
        mapUtil.setColors(colorStrings === undefined ? ['#005aff'] : colorStrings);

        //初始化参数
        points = [];
        regions = [];
        lines = [];

        locationPoint = undefined;
        dynamicLine = undefined;

        defaultLngLatAlt = new AMap.LngLat(116.38, 39.9);  //北京市中心

    }).catch(err => {
        console.log(err)
    });
};

/**
 * 销毁地图
 */
mapUtil.destroy = function () {
    if (map != null) {
        console.log('销毁地图！！');
        map.destroy();
        map = null;
    }
};

/**
 * 设置地图的图标集
 * @param iconInfos 图标信息
 */
mapUtil.setIcons = function (iconInfos) {
    icons = [];
    iconOffsets = [];
    let size;
    for (let i = 0; i < iconInfos.length; i++) {
        size = new AMap.Size(iconInfos[i].width, iconInfos[i].height);
        icons.push(new AMap.Icon({
            size: size,    // 图标尺寸
            image: iconInfos[i].path,
            imageSize: size   // 根据所设置的大小拉伸或压缩图片
        }));
        //记录图标的偏移量
        iconOffsets.push(new AMap.Pixel(-Math.ceil(iconInfos[i].width / 2), -iconInfos[i].height));
    }
};

mapUtil.setColors = function (colorStrings) {
    colors = colorStrings;
};

mapUtil.moveToPoint = function (zoom, longitude, latitude, altitude, altitudeAmend) {
    map.setZoomAndCenter(zoom,wgsToGcj(longitude,latitude, altitude, altitudeAmend));
};

/**
 * 重置视角
 */
mapUtil.fitView = function () {
    map.setFitView();
};

/**
 * 重置视角
 */
mapUtil.fitViewWithRegionAndLines = function () {
    let fitObjects = [];
    for (let i = 0; i < regions.length; i++) {
        if(regions[i].getExtData().visible){
            fitObjects.push(regions[i]);
        }
    }

    for (let i = 0; i < lines.length; i++) {
        if(lines[i].getExtData().visible){
            fitObjects.push(lines[i]);
        }
    }
    map.setFitView(fitObjects,true,[0,0,0,0],18);
};

/**
 * 设置定位点坐标
 * @param lngLatAlt gcj坐标
 * @param iconIndex
 */
mapUtil.setLocationPointWithGcj = function(lngLatAlt, iconIndex) {
    if (locationPoint === undefined) {
        //新建locationPoint
        locationPoint = new AMap.Marker({
            position: getValidLngLatAlt(lngLatAlt),
            icon: getValidIcon(iconIndex),
            offset: getValidIconOffset(iconIndex)
        });

        //加入地图
        map.add(locationPoint);

        console.log("添加定位点");
    }

    if (lngLatAlt === undefined) {
        locationPoint.hide();
    } else {
        locationPoint.setPosition(lngLatAlt);
        locationPoint.show();
    }
};

/**
 * 清除定位点
 */
mapUtil.clearLocationPoint = function() {
    if (locationPoint != undefined) {
        //从地图中删除定位点
        map.remove(locationPoint);

        console.log('清理定位点');

        locationPoint = undefined;
    }
};

/**
 * 添加标记点
 * @param lngLatAlt gcj坐标
 * @param iconIndex 使用的图标数组中的index
 * @param text 文本标签
 * @param onClick 点击事件的回调函数
 * @param customKey 自定义Id
 */
mapUtil.addPointWithGcj = function(lngLatAlt, iconIndex, text, onClick, customKey) {
    //新建point
    let point = new AMap.Marker({
        position: getValidLngLatAlt(lngLatAlt),
        icon: getValidIcon(iconIndex),
        offset: getValidIconOffset(iconIndex)
    });

    //添加文字标签
    if (text) {
        mapUtil.setPointText(point, text);
    }

    //绑定事件函数
    if (onClick) {
        point.on('click', onClick);
    }

    //设置在数组中的顺序
    point.setExtData({
        index: points.length,
        customKey: customKey
    });

    //加入数组
    points.push(point);

    //加入地图
    map.add(point);

    if (lngLatAlt === undefined) {
        point.hide();
    }

    console.log("添加点：当前数量", points.length);
};

/**
 * 添加标记点
 * @param longitude GPS精度
 * @param latitude GPS纬度
 * @param altitude GPS海拔
 * @param altitudeAmend 海拔修正
 * @param iconIndex 使用的图标数组中的index
 * @param text 文本标签
 * @param onClick 点击事件的回调函数
 * @param customKey 自定义ID
 */
mapUtil.addPoint = function(longitude, latitude, altitude, altitudeAmend, iconIndex, text, onClick, customKey) {
    mapUtil.addPointWithGcj(wgsToGcj(longitude, latitude, altitude, altitudeAmend), iconIndex, text, onClick, customKey);
};

/**
 * 添加多个标记点
 * @param positions GPS坐标
 * @param altitudeAmend 海拔修正
 * @param iconIndexes
 * @param texts
 * @param onClick
 * @param customKeys 自定义IDs
 */
mapUtil.addPoints = function(positions, altitudeAmend, iconIndexes, texts, onClick, customKeys) {
    if (positions && positions.length % 3 === 0) {
        let positionIndex = 0;
        let count = positions.length / 3;
        for (let i = 0; i < count; i++) {
            mapUtil.addPointWithGcj(wgsToGcj(positions[positionIndex], positions[positionIndex + 1], positions[positionIndex + 2], altitudeAmend),
                tryGetFromArray(iconIndexes, i), tryGetFromArray(texts, i), onClick, tryGetFromArray(customKeys, i));
            positionIndex += 3;
        }
    }
};

/**
 * 设置点的文本标签
 * @param point
 * @param text
 */
mapUtil.setPointText = function(point, text) {
    point.setLabel({
        // offset: new AMap.Pixel(0,0),  //设置文本标注偏移量
        content: "<div class='info'>"+text+"</div>", //设置文本标注内容
        direction: 'top' //设置文本标注方位
    });
};

/**
 * 设置点的图标
 * @param pointIndex 点在数组中的index
 * @param iconIndex 图标在图标数组中的index
 */
mapUtil.setPointIcon = function(pointIndex, iconIndex) {
    if (pointIndex >= 0 && pointIndex < points.length) {
        points[pointIndex].setIcon(icons[iconIndex]);
    }
};

/**
 * 设置点的图标和位置
 * @param pointIndex 点在数组中的index
 * @param iconIndex 图标在图标集合中的index
 * @param longitude GPS精度
 * @param latitude GPS纬度
 * @param altitude GPS海拔
 */
mapUtil.setPointIconAndPosition = function(pointIndex, longitude, latitude, altitude, altitudeAmend, iconIndex) {
    if (pointIndex >= 0 && pointIndex < points.length) {
        let point = points[pointIndex];
        let lngLatAlt = wgsToGcj(longitude, latitude, altitude, altitudeAmend);


        if (lngLatAlt != undefined && iconIndex >= 0 && iconIndex < icons.length) {
            point.setIcon(icons[iconIndex]);
            point.setPosition(lngLatAlt);

            if (!point.getOptions().visible) {
                point.show();
            }
        } else {
            if (point.getOptions().visible) {
                point.hide();
            }
        }
    }
};

/**
 * 设置多个点的图标和位置
 * @param startPointIndex 开始点在集合中的index
 * @param positions 所有点的新位置集合
 * @param altitudeAmend 海拔修正
 * @param iconIndexs 所有点的图标在图标数组中的index集合
 */
mapUtil.setPointPositionsAndIcons = function(startPointIndex, positions, altitudeAmend, iconIndexs) {
    let positionIndex = 0;
    for (let i = 0; i < iconIndexs.length; i++) {
        mapUtil.setPointIconAndPosition(startPointIndex + i, positions[positionIndex], positions[positionIndex + 1], positions[positionIndex + 2], altitudeAmend, iconIndexs[i]);
        positionIndex += 3;
    }
};

/**
 * 显示点
 * @param pointIndex
 */
mapUtil.pointShow = function(pointIndex) {
    if (pointIndex >= 0 && pointIndex < points.length) {
        points[pointIndex].show();
    }
};

/**
 * 隐藏点
 * @param pointIndex
 */
mapUtil.pointHide = function(pointIndex) {
    if (pointIndex >= 0 && pointIndex < points.length) {
        points[pointIndex].hide();
    }
};

/**
 * 删除点
 * @param pointIndex 点在数组中的index
 */
mapUtil.deletePoint = function(pointIndex) {
    if (pointIndex >= 0 && pointIndex < points.length) {
        //从地图中删除点
        map.remove(points[pointIndex]);

        //从数组中删除点
        points.splice(pointIndex, 1);

        //调整点的index
        for (let i = pointIndex; i < points.length; i++) {
            points[i].getExtData().index = i;
        }

        console.log("删除点：index=" + pointIndex + "，当前数量：" + points.length);
    }
};

/**
 * 清除所有的点
 */
mapUtil.clearPoint = function() {
    if (points.length > 0) {
        //从地图中删除所有点
        map.remove(points);

        console.log('清理点，清理数量：', points.length);

        //清空数组
        points = [];
    }
};

/**
 * 添加线
 * @param pathPoints 线的连接点集合（gcj坐标）
 * @param colorIndex 线的颜色在颜色集合中的index
 * @param width 线的宽度
 */
mapUtil.addLineWithGcj = function(pathPoints, colorIndex, width) {
    //新建point
    let line = new AMap.Polyline({
        path: pathPoints === undefined ? [defaultLngLatAlt] : pathPoints,
        strokeColor: getValidColor(colorIndex),
        strokeWeight: width ? width : 2
    });

    //设置在数组中的顺序和是否显示
    line.setExtData({
        index: lines.length,
        visible: true
    });

    //注册显示隐藏函数，主要是控制ExtData.visible
    registerShowAndHideEvent(line);

    //加入数组
    lines.push(line);

    //加入地图
    map.add(line);

    if (pathPoints === undefined) {
        line.hide();
    }

    console.log("添加线：当前数量", lines.length);
};

/**
 * 添加线
 * @param pathPoints 线的连接点集合
 * @param altitudeAmend 海拔修正
 * @param colorIndex 线的颜色在颜色集合中的index
 * @param width 线的宽度
 */
mapUtil.addLine = function(pathPoints,altitudeAmend, colorIndex, width) {
    if (pathPoints && pathPoints.length > 0 && pathPoints.length % 3 === 0) {
        mapUtil.addLineWithGcj(tryWgsToGcjs(pathPoints,altitudeAmend), colorIndex, width);
    }
};

/**
 * 添加多条线
 * @param pathPoints 线的连接点集合
 * @param altitudeAmend 海拔修正
 * @param pathPointCounts 每条线的点数集合
 * @param colorIndexs 线的颜色index集合
 * @param widths 线的宽度集合
 */
mapUtil.addLines = function(pathPoints, altitudeAmend, pathPointCounts, colorIndexs, widths) {
    if (pathPoints && pathPoints.length > 0 && pathPoints.length % 3 === 0) {
        let startIndex = 0;
        for (let i = 0; i < pathPointCounts.length; i++) {
            mapUtil.addLineWithGcj(tryWgsToGcjs(pathPoints.slice(startIndex, startIndex + pathPointCounts[i] * 3),altitudeAmend), tryGetFromArray(colorIndexs, i), tryGetFromArray(widths, i));
            startIndex += pathPointCounts[i] * 3;
        }
    }
};

/**
 * 设置线的路径
 * @param lineIndex 线在数组中的index
 * @param pathPoints 线的连接点集合（火星坐标）
 * @param colorIndex 线的颜色在颜色集合中的index
 * @param width 线的宽度
 */
mapUtil.setLinePositionWithGcj = function(lineIndex, pathPoints, colorIndex, width) {
    if (lineIndex >= 0 && lineIndex < lines.length) {
        let line = lines[lineIndex];
        if (pathPoints === undefined) {
            if (line.getExtData().visible) {
                line.hide();
            }
        } else {
            line.setPath(pathPoints);
            if (colorIndex != undefined) {
                line.setOptions({
                    strokeColor: getValidColor(colorIndex)
                });
            }
            if (width != undefined) {
                line.setOptions({
                    strokeWeight: width
                });
            }
            if (!line.getExtData().visible) {
                line.show();
            }
        }
    }
};

/**
 * 设置线的路径
 * @param lineIndex 线在数组中的index
 * @param pathPoints 线的连接点集合
 * @param altitudeAmend 海拔修正
 * @param colorIndex 线的颜色在颜色集合中的index
 * @param width 线的宽度
 */
mapUtil.setLinePosition = function(lineIndex, pathPoints, altitudeAmend, colorIndex, width) {
    if (pathPoints && pathPoints.length % 3 === 0) {
        mapUtil.setLinePositionWithGcj(lineIndex, tryWgsToGcjs(pathPoints,altitudeAmend), colorIndex, width);
    }
};

/**
 * 设置多条线的路径
 * @param startLineIndex
 * @param pathPoints
 * @param altitudeAmend 海拔修正
 * @param pathPointCounts
 * @param colorIndexs 线的颜色index集合
 * @param widths 线的宽度集合
 */
mapUtil.setLinePositions = function(startLineIndex, pathPoints, altitudeAmend, pathPointCounts, colorIndexs, widths) {
    if (pathPoints && pathPoints.length % 3 === 0) {
        let startIndex = 0;
        for (let i = 0; i < pathPointCounts.length; i++) {
            mapUtil.setLinePositionWithGcj(startLineIndex + i, tryWgsToGcjs(pathPoints.slice(startIndex, startIndex + pathPointCounts[i] * 3),altitudeAmend), tryGetFromArray(colorIndexs, i), tryGetFromArray(widths, i));
            startIndex += pathPointCounts[i] * 3;
        }
    }
};

/**
 * 删除线
 * @param lineIndex 线在数组中的index
 */
mapUtil.deleteLine = function(lineIndex) {
    if (lineIndex >= 0 && lineIndex < lines.length) {
        //从地图中删除线
        map.remove(lines[lineIndex]);

        //从数组中删除线
        lines.splice(lineIndex, 1);

        //调整线的index
        for (let i = lineIndex; i < lines.length; i++) {
            lines[i].getExtData().index = i;
        }

        console.log("删除线：index=" + lineIndex + "，当前数量：" + lines.length);
    }
};

/**
 * 清除所有的线
 */
mapUtil.clearLine = function() {
    if (lines.length > 0) {
        //从地图中删除所有线
        map.remove(lines);

        console.log('清理线，清理数量：', lines.length);

        //清空数组
        lines = [];
    }
};

/**
 * 更新动态线（火星坐标）
 * @param pathPoints
 * @param colorIndex
 * @param width
 */
mapUtil.changeDynamicLineWithGcj = function(pathPoints, colorIndex, width) {
    if (pathPoints && pathPoints.length > 0) {
        if (!dynamicLine) {
            //新建动态线
            dynamicLine = new AMap.Polyline({
                path: pathPoints,
                strokeColor: getValidColor(colorIndex),
                strokeWeight: width ? width : 2
            });

            //加入地图
            map.add(dynamicLine);

            console.log("新建动态线！！");
        }

        dynamicLine.setPath(pathPoints);
        console.log("更新动态线！！");

    } else {
        mapUtil.clearDynamicLine();
    }
};

/**
 * 更新动态线
 * @param pathPoints
 * @param altitudeAmend 海拔修正
 * @param colorIndex
 * @param width
 */
mapUtil.changeDynamicLine = function(pathPoints, altitudeAmend, colorIndex, width) {
    if (pathPoints && pathPoints.length % 3 ===0) {
        mapUtil.changeDynamicLineWithGcj(tryWgsToGcjs(pathPoints, altitudeAmend), colorIndex, width);
    }
};

/**
 * 清除动态线
 */
mapUtil.clearDynamicLine = function() {
    if (dynamicLine) {

        //从地图中删除
        map.remove(dynamicLine);

        dynamicLine = undefined;

        console.log("删除动态线！！");
    }
};

/**
 * 添加区域
 * @param peakPoints 顶点坐标集合（火星坐标）
 * @param lineColorIndex
 * @param fillColorIndex
 * @param onClick
 */
mapUtil.addRegionWithGcj = function(peakPoints, lineColorIndex, fillColorIndex, onClick) {
    let region = new AMap.Polygon({
        path: peakPoints === undefined ? [defaultLngLatAlt] : peakPoints,
        strokeColor: getValidColor(lineColorIndex),
        fillColor: getValidColor(fillColorIndex)
    });

    //绑定事件函数
    if (onClick) {
        region.on('click', onClick);
    }

    //设置在数组中的顺序和是否显示
    region.setExtData({
        index: regions.length,
        visible: true
    });

    //注册显示隐藏函数，主要是控制ExtData.visible
    registerShowAndHideEvent(region);

    //加入数组
    regions.push(region);

    //加入地图
    map.add(region);

    if (peakPoints === undefined) {
        region.hide();
    }

    console.log("添加区域：当前数量", regions.length);
};

/**
 * 添加区域
 * @param peakPoints
 * @param altitudeAmend 海拔修正
 * @param lineColorIndex
 * @param fillColorIndex
 * @param onClick
 */
mapUtil.addRegion = function(peakPoints, altitudeAmend, lineColorIndex, fillColorIndex, onClick) {
    if (peakPoints && peakPoints.length > 0 && peakPoints.length % 3 === 0) {
        mapUtil.addRegionWithGcj(tryWgsToGcjs(peakPoints,altitudeAmend), lineColorIndex, fillColorIndex, onClick);
    }
};

/**
 * 添加多个区域
 * @param peakPoints
 * @param altitudeAmend 海拔修正
 * @param peakPointCounts
 * @param lineColorIndexs
 * @param fillColorIndexs
 * @param onClick
 */
mapUtil.addRegions = function(peakPoints, altitudeAmend, peakPointCounts, lineColorIndexs, fillColorIndexs, onClick) {
    if (peakPoints && peakPoints.length > 0 && peakPoints.length % 3 === 0) {
        let startIndex = 0;
        for (let i = 0; i < peakPointCounts.length; i++) {
            mapUtil.addRegionWithGcj(tryWgsToGcjs(peakPoints.slice(startIndex, startIndex + peakPointCounts[i] * 3),altitudeAmend), tryGetFromArray(lineColorIndexs, i), tryGetFromArray(fillColorIndexs, i), onClick);
            startIndex += peakPointCounts[i] * 3;
        }
    }
};

/**
 * 设置区域的透明度
 * @param regionIndex
 * @param opacity
 */
mapUtil.setRegionOpacity =  function(regionIndex, colorIndex, opacity) {
    if (regionIndex >= 0 && regionIndex < regions.length) {
        regions[regionIndex].setOptions({
            fillColor: getValidColor(colorIndex),
            fillOpacity: opacity
        });
    }
};

/**
 * 删除区域
 * @param regionIndex 区域在数组中的index
 */
mapUtil.deleteRegion = function(regionIndex) {
    if (regionIndex >= 0 && regionIndex < regions.length) {
        //从地图中删除区域
        map.remove(regions[regionIndex]);

        //从数组中删除区域
        regions.splice(regionIndex, 1);

        //调整区域的index
        for (let i = regionIndex; i < regions.length; i++) {
            regions[i].getExtData().index = i;
        }

        console.log("删除区域：index=" + regionIndex + "，当前数量：" + regions.length);
    }
};

/**
 * 清除所有的区域
 */
mapUtil.clearRegion = function() {
    if (regions.length > 0) {
        //从地图中删除所有区域
        map.remove(regions);

        console.log('清理区域，清理数量：', regions.length);

        //清空数组
        regions = [];
    }
};

/**
 *
 * @param lnglatalts
 * @param altitudeAmend
 * @returns {Array}
 */
export function wgsToGcjs(lnglatalts, altitudeAmend) {
    let dlnglat;
    let radlat;
    let magic;
    let resultArray = [];
    for (let i = 0; i < lnglatalts.length; i += 3) {
        if (isOutChina(lnglatalts, i)) {
            resultArray.push(undefined);
        } else {
            dlnglat = transformLnglat(lnglatalts[i] - 105.0, lnglatalts[i + 1] - 35.0, altitudeAmend > -1000 ? altitudeAmend : lnglatalts[i + 2]);

            radlat = lnglatalts[i + 1] * 0.0174532925199433;
            magic = Math.sin(radlat);
            magic = Math.sqrt(1 - 0.00669342162296604 * magic * magic);

            resultArray.push(new AMap.LngLat(lnglatalts[i] + (dlnglat[0] * 180.0 * magic) / (Math.cos(radlat) * 20037847.6347958),
                lnglatalts[i + 1] + (dlnglat[1] * 180.0 * magic * magic * magic) / 19903725.8721594));
        }
    }
    return resultArray;
}

/**
 *
 * @param lnglatalts
 * @param altitudeAmend
 * @returns {*}
 */
function tryWgsToGcjs(lnglatalts, altitudeAmend) {
    let dlnglat;
    let radlat;
    let magic;
    let resultArray = [];
    for (let i = 0; i < lnglatalts.length; i += 3) {
        if (isOutChina(lnglatalts, i)) {
            return undefined;
        } else {
            dlnglat = transformLnglat(lnglatalts[i] - 105.0, lnglatalts[i + 1] - 35.0, altitudeAmend > -1000 ? altitudeAmend : lnglatalts[i + 2]);

            radlat = lnglatalts[i + 1] * 0.0174532925199433;
            magic = Math.sin(radlat);
            magic = Math.sqrt(1 - 0.00669342162296604 * magic * magic);

            resultArray.push(new AMap.LngLat(lnglatalts[i] + (dlnglat[0] * 180.0 * magic) / (Math.cos(radlat) * 20037847.6347958),
                lnglatalts[i + 1] + (dlnglat[1] * 180.0 * magic * magic * magic) / 19903725.8721594));
        }
    }
    return resultArray;
}

export function wgsToGcj(longitude, latitude, altitude, altitudeAmend) {
    if (isOutChinaLnglat(longitude, latitude, altitude)) {
        return undefined;
    }

    let dlnglat = transformLnglat(longitude - 105.0, latitude - 35.0, altitudeAmend > -1000 ? altitudeAmend : altitude);

    let radlat = latitude * 0.0174532925199433;
    let magic = Math.sin(radlat);
    magic = Math.sqrt(1 - 0.00669342162296604 * magic * magic);

    return new AMap.LngLat(longitude + (dlnglat[0] * 180.0 * magic) / (Math.cos(radlat) * 20037847.6347958),
        latitude + (dlnglat[1] * 180.0 * magic * magic * magic) / 19903725.8721594);
}

function transformLnglat(lng, lat, alt) {
    let lngPI = lng * 3.14159265358979;
    let latPI = lat * 3.14159265358979;
    let temp = 20.0 * Math.sin(lngPI * 6.0) + 20.0 * Math.sin(lngPI * 2.0);
    let sqrtlng = Math.sqrt(Math.abs(lng));
    return [300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * sqrtlng
    + (temp + 20.0 * Math.sin(lngPI)
        + 40.0 * Math.sin(lngPI / 3.0)
        + 150.0 * Math.sin(lngPI / 12.0)
        + 300.0 * Math.sin(lngPI / 30.0)) * 0.666666666666667 + alt * 0.001,
        -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * sqrtlng
        + (temp + 20.0 * Math.sin(latPI)
            + 40.0 * Math.sin(latPI / 3.0)
            + 160.0 * Math.sin(latPI / 12.0)
            + 320.0 * Math.sin(latPI / 30.0)) * 0.666666666666667 + alt * 0.001];
}

function tryGetFromArray(array, index) {
    return array === undefined ? undefined : (isArray(array) ? array[index] : array);
}

function isArray(array){
    return Object.prototype.toString.call(array) == '[object Array]';
}

function getValidLngLatAlt(lngLatAlt){
    return lngLatAlt === undefined ? defaultLngLatAlt : lngLatAlt;
}

function getValidIcon(iconIndex) {
    if (iconIndex === undefined) {
        return icons[0];
    }
    return (iconIndex > -1 && iconIndex < icons.length) ? icons[iconIndex] : icons[0];
}

function getValidIconOffset(iconIndex) {
    if (iconIndex === undefined) {
        return iconOffsets[0];
    }
    return (iconIndex > -1 && iconIndex < iconOffsets.length) ? iconOffsets[iconIndex] : iconOffsets[0];
}

function getValidColor(colorIndex) {
    if (colorIndex === undefined) {
        return colors[colorIndex];
    }
    return (colorIndex > -1 && colorIndex < colors.length) ? colors[colorIndex] : colors[0];
}

function registerShowAndHideEvent(shape) {
    shape.on('show', (event) => {
        event.target.getExtData().visible = true;
    });
    shape.on('hide', (event) => {
        event.target.getExtData().visible = false;
    });
}

function isOutChina(points, startIndex) {
    return points[startIndex] < 73.5 || points[startIndex] > 135.1
        || points[startIndex + 1] < 3.8 || points[startIndex + 1] > 53.6;
    // || points[startIndex + 2] < -1000.0 || points[startIndex + 2] > 8900.0;
}

function isOutChinaLnglat(longitude, latitude, altitude) {
    return longitude < 73.5 || longitude > 135.1
        || latitude < 3.8 || latitude > 53.6;
    // || altitude < -1000.0 || altitude > 8900.0;
}