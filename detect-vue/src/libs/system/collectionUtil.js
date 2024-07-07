/**
 * 拼接数组
 * @param source 原数组
 * @param needAppend 需要拼接的数组
 */
export function append(source, needAppend) {
    for (let i = 0; i < needAppend.length; i++) {
        source.push(needAppend[i]);
    }
}

/**
 * 拼接数组（去掉重复元素）
 * @param source 原数组
 * @param needAppend 需要拼接的数组
 */
export function appendRemoveDuplicate(source, needAppend) {
    for (let i = 0; i < needAppend.length; i++) {
        if (!source.includes(needAppend[i])) {
            source.push(needAppend[i]);
        }
    }
}

/**
 * 拼接数组（去掉重复元素）
 * @param source 原数组
 * @param needAppend 需要拼接的数组
 * @returns any[] 拼接出来的新数组
 */
export function concatRemoveDuplicate(source, needAppend) {
    let concat = pickWithStartAndLength(source, 0, source.length);
    appendRemoveDuplicate(concat, needAppend);
    return concat;
}

/**
 * 拆分数组
 * @param source 原数组
 * @param eachLength 分组长度
 * @returns any[] 拆分后的数组
 */
export function splitWithFixLength(source, eachLength) {
    let split = new Array(counts.length);
    let start = 0;
    for (let i = 0; i < counts.length; i++) {
        split[i] = pickWithStartAndLength(source, start, counts[i]);
        start += counts[i];
    }
    return split;
}

/**
 * 拆分数组
 * @param source 原数组
 * @param counts 长度集合
 * @returns any[] 拆分后的数组
 */
export function splitWithCount(source, counts) {
    let split = new Array(counts.length);
    let start = 0;
    for (let i = 0; i < counts.length; i++) {
        split[i] = pickWithStartAndLength(source, start, counts[i]);
        start += counts[i];
    }
    return split;
}

/**
 * 拆分数组
 * @param source 原数组
 * @param startAndEnds 开始和结束的位置
 * @returns any[] 拆分后的数组
 */
export function splitWithCount(source, startAndEnds) {
    let split = new Array(startAndEnds.length / 2);
    let index = 0;
    for (let i = 0; i < startAndEnds.length; i += 2) {
        split[index] = pickWithStartAndEnd(source, startAndEnds[i], startAndEnds[i + 1]);
        index++;
    }
    return split;
}

/**
 * 提取数组
 * @param source 原数组
 * @param start 开始位置
 * @param length 提取长度
 * @returns any[] 提取的新数组
 */
export function pickWithStartAndLength(source, start, length) {
    let pick = new Array(length);
    for (let i = 0; i < length; i++) {
        pick[i] = source[start + i];
    }
    return pick;
}

/**
 * 提取数组
 * @param source 原数组
 * @param start 开始位置
 * @param end 结束位置（不包含）
 * @returns any[] 提取的新数组
 */
export function pickWithStartAndEnd(source, start, end) {
    return pickWithStartAndLength(source, start, end - start);
}