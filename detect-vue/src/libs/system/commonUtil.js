import Setting from '@/setting';

export function deepClone(obj) {
    return JSON.parse(JSON.stringify(obj))
}

/**
    函数:格式化字符串
    参数：str:字符串模板； data:数据
    调用方式:formatString("api/values/{id}/{name}",{id:101,name:"test"});
             formatString("api/values/{0}/{1}",101,"test");
*/
export function format(str, data) {
    if (!str || !data) {
        return str;
    }

    if (typeof data === "object") {
        for (let key in data) {
            if (data.hasOwnProperty(key)) {
                str = str.replace(new RegExp("\{" + key + "\}", "g"), data[key]);
            }
        }
        return str;
    } else {
        let args = arguments;
        let reg = new RegExp("\{([0-" + (args.length - 1) + "])\}", "g");
        return str.replace(reg, function(match, index) {
            return args[index - (-1)];
        });
    }
}

export function getProjectUrl(url) {
    return Setting.routerBase+'/'+url;
}