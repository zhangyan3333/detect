<template>
    <div>
        <Row :gutter="5">
            <Col span="6">
                <Card dis-hover>
                    <div style="height: 630px; overflow: auto">
                        <Button v-if="!this.info.organizationId" icon="md-add" style="margin-bottom: 12px" type="success" @click="insertOrganization(true)">{{ $t('page.common.add') + $t('page.organization.root') }}</Button>
                        <Tree :data="nodes" @on-contextmenu="showContextMenu" @on-select-change="showOrganization">
                            <template slot="contextMenu">
                                <DropdownItem @click.native="insertOrganization(false)" style="color: #19be6b">
                                    <Icon type="md-add"></Icon>
                                    {{ $t('page.common.add') + $t('page.organization.sub') }}
                                </DropdownItem>
                                <DropdownItem @click.native="deleteOrganization" style="color: #ed4014" divided>
                                    <Icon type="md-close"></Icon>
                                    {{ $t('page.common.delete') + $t('page.organization.entityDescription') }}
                                </DropdownItem>
                            </template>
                        </Tree>
                    </div>
                </Card>
            </Col>
            <Col span="18">
                <Card dis-hover>
                    <template slot="title">
                        <p>{{ formTitle }}</p>
                    </template>
                    <div style="height: 580px; overflow: auto">
                        <Alert v-show="!isFormShow">{{ $t('page.organization.noticeMessage') }}</Alert>
                        <Form ref="formValidate" :model="currentNode" :label-width="80" v-show="isFormShow" :rules="ruleValidate">
                            <FormItem :label="$t('page.organization.entityDescription') + $t('page.common.name')" prop="title">
                                <Input v-model="currentNode.title" :placeholder="$t('page.organization.entityDescription') + $t('page.common.name')"></Input>
                            </FormItem>
                            <FormItem>
                                <Button :type="isInsert ? 'success' : 'warning'" @click="ok">{{ isInsert ? $t('page.common.add') : $t('page.common.modify')}}</Button>
                            </FormItem>
                        </Form>
                    </div>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import { mapState, mapActions } from 'vuex';
    import { nodeToOrganizationMapping, organizationToNodeMapping, entityTreeToNodeTree } from '@/libs/system/treeUtil';
    import { deepClone, format } from '@/libs/system/commonUtil';
    import { entityRequest } from '@/libs/system/requestUtil';

    export default {
        name: 'system-organization',
        data() {
            return {
                apiBasePath: 'organizations',
                formTitle: $t('page.organization.manage') + $t('page.organization.entityDescription'),
                isInsert: false,
                isFormShow: false,
                currentNode: {},
                rightClickedNode: {},
                requestFunction: {},
                nodes: [],
                ruleValidate: {
                    title: [
                        { required: true, message: format($t('page.common.notNullTemplate'), {propertyName: $t('page.organization.entityDescription') + $t('page.common.name') }), trigger: 'blur' }
                    ]
                }
            }
        },
        mounted() {
            this.query();
        },
        computed: {
            ...mapState('admin/user', [
                'info'
            ])
        },
        methods: {
            showOrganization(clickedNodes) {
                if (clickedNodes.length > 0) {
                    this.$refs.formValidate.resetFields();
                    this.currentNode = deepClone(clickedNodes[0]);
                    this.formTitle = $t('page.common.modify') + $t('page.organization.entityDescription') + ' - ' + this.currentNode.title;
                    this.isInsert = false;
                    this.isFormShow = true;
                } else {
                    this.formTitle= $t('page.organization.manage') + $t('page.organization.entityDescription');
                    this.isFormShow = false;
                }
            },
            showContextMenu(node) {
                this.rightClickedNode = node;
            },
            insertOrganization(isRoot) {
                this.$refs.formValidate.resetFields();
                this.formTitle = $t('page.common.add') + (isRoot ? $t('page.organization.root') : ($t('page.organization.sub') + ' - ' + this.rightClickedNode.title));
                this.isInsert = true;
                this.isFormShow = true;
                this.currentNode = {};
            },
            deleteOrganization() {
                this.$Modal.confirm({
                    title: $t('page.common.delete'),
                    content: format($t('page.common.deleteConfirmTemplate'), {
                        entityName: this.rightClickedNode.title,
                        entityDescription: $t('page.organization.entityDescription')
                    }),
                    onOk: () => {
                        // 第三个参数是onSuccess
                        entityRequest('delete', this.apiBasePath, this.rightClickedNode.value, this.query);
                    }
                });
            },
            ok() {
                this.$refs.formValidate.validate((valid) => {
                    if (valid) {
                        if (this.isInsert) {
                            this.currentNode.parentId = this.rightClickedNode.value;
                        }
                        entityRequest(this.isInsert ? 'insert':'update', this.apiBasePath, nodeToOrganizationMapping(this.currentNode),
                            //第四个参数, onSuccess
                            () => {
                                this.query();
                                if (this.isInsert) {
                                    this.currentNode = {};
                                }
                            });

                    }
                });
            },
            query() {
                entityRequest('get', this.apiBasePath, this.info.organizationId,
                    // 第四个参数, onSuccess
                    (response) => {
                        this.nodes = entityTreeToNodeTree(response.data, organizationToNodeMapping);
                    });
            }
        }
    }
</script>