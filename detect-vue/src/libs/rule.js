export const validateInt = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('不能为空'));
    }
    if (!Number.isInteger(Number(value))) {
        callback(new Error('请输入一个整数'));
    } else {
        callback();
    }
};

export const validateDouble = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('不能为空'));
    }
    if (!Number(value)) {
        callback(new Error('请输入一个浮点数'));
    } else {
        callback();
    }
};

export const validateLength = (rule, value, callback) => {
    if (!value || value.length < 1) {
        return callback(new Error('不能为空'));
    }else {
        callback();
    }
};

export const validateTowerType = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('不能为空'));
    }
    if (!Number.isInteger(Number(value))) {
        callback(new Error('请输入一个整数'));
    } else {
        if(Number(value)<0 || Number(value) > 100){
            callback(new Error('请输入0-100之间的值'));
        }else {
            callback();
        }
    }
};

export const multiSelect = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('不能为空'));
    }
    if (value.length < 1) {
        callback(new Error('请至少选择一个选项'));
    } else {
        callback();
    }
};

export const treeSelect = (rule, value, callback) => {
    if (!value) {
        return callback(new Error('不能为空'));
    }else {
        callback();
    }
};

export const timeRange = (rule, value, callback) => {
    console.log('timeRange:',value)
    if (!value) {
        return callback(new Error('不能为空'));
    }
    if (value[0].length < 1) {
        return callback(new Error('不能为空'));
    }
    else {
        callback();
    }
};
