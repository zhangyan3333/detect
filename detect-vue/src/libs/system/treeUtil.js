/**
 * 单个节点到单个机构的映射关系
 * @param node 节点
 * @returns
 */
export function nodeToOrganizationMapping(node) {
    if (node.value) {
        return {
            id: node.value,
            createTime: node.createTime,
            name: node.title,
            parentId: node.parentId ? node.parentId : 0
        };
    } else {
        return {
            name: node.title,
            parentId: node.parentId ? node.parentId : 0
        };
    }
}

/**
 * 单个机构到单个节点的映射关系
 * @param entity 机构
 * @returns
 */
export function organizationToNodeMapping(entity) {
    return {
        value: entity.id,
        createTime: entity.createTime,
        title: entity.name,
        parentId: entity.parentId,
        expand: true,
        contextmenu: true
    };
}

/**
 * 将实体树转换为iview组件可以显示的树
 * @param entityTree 实体树
 * @param entityToNodeMapping 单个实体到单个节点的映射关系
 * @returns iview组件可以显示的树的跟节点列表
 */
export function entityTreeToNodeTree(entityTree, entityToNodeMapping) {
    let nodeTree = [];

    if (entityTree.length) {
        for (let i = 0; i < entityTree.length; i++) {
            nodeTree[i] = entityToNodeMapping(entityTree[i]);

            if (entityTree[i].children.length > 0) {
                nodeTree[i].children = entityTreeToNodeTree(entityTree[i].children, entityToNodeMapping);
            }
        }
    } else {
        nodeTree[0] = entityToNodeMapping(entityTree);
        if (entityTree.children.length > 0) {
            nodeTree[0].children = entityTreeToNodeTree(entityTree.children, entityToNodeMapping);
        }
    }

    return nodeTree;
}

export function findInNodeTree(nodeTree, nodeValue) {
    if (nodeValue === undefined) {
        return undefined;
    }

    if (nodeValue.trim() === '') {
        return undefined;
    }

    let findNode;
    if (nodeTree.length) {
        for (let i = 0; i < nodeTree.length; i++) {
            if (nodeTree[i].value === nodeValue) {
                return nodeTree[i];
            }

            if (nodeTree[i].children && nodeTree[i].children.length > 0) {
                findNode = findInNodeTree(nodeTree[i].children, nodeValue);
                if (findNode) {
                    return findNode;
                }
            }
        }
    } else {
        if (nodeTree.value === nodeValue) {
            return nodeTree;
        }

        if (nodeTree.children && nodeTree.children.length > 0) {
            findNode = findInNodeTree(nodeTree.children, nodeValue);
            if (findNode) {
                return findNode;
            }
        }
    }

    return undefined;
}

export function treeToList(node, nodeMapping) {
    let result = [];

    if (node === undefined) {
        return result;
    }

    innerTreeToList(node, nodeMapping, result);
    return result;
}

function innerTreeToList(node, nodeMapping, result) {
    if (node.length) {
        for (let i = 0; i < node.length; i++) {
            result.push(nodeMapping(node[i]));
            if (node[i].children && node[i].children.length > 0) {
                innerTreeToList(node[i].children, nodeMapping, result);
            }
        }
    } else {
        result.push(nodeMapping(node));
        if (node.children && node.children.length > 0) {
            innerTreeToList(node.children, nodeMapping, result);
        }
    }
}