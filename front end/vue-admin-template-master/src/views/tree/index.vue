<template>
  <el-container style="height:673px; border: 1px solid #eee;">
    <el-aside width="200px" style="background-color: rgb(238,241,246);">
      <!-- <el-input
        placeholder="输入关键字进行过滤"
        v-model="filterText">
      </el-input> -->
      <el-button type="text" @click="dialogFormVisible = true">新建文件夹 +</el-button>
      <el-button type="text" @click="dialogFormVisibles = true">新建文件 +</el-button>
      <el-dialog title="创建文件夹" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="文件夹名称：" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogProject()">取 消</el-button>
          <el-button type="primary" @click="() => append(data)">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="创建文件" :visible.sync="dialogFormVisibles">
        <el-form :model="form" :label-position="labelPosition"> 
          <el-form-item label="选择文件夹：" :label-width="formLabelWidth">
            <el-select v-model="dialogvalue" placeholder="请选择">
              <el-option
                v-for="item in data"
                :label="item.label"
                :key="item.value"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="文件名称：" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFile()">取 消</el-button>
          <el-button type="primary" @click="appends()">确 定</el-button>
        </div>
      </el-dialog>
      <el-tree
        class="filter-tree"
        :data="data"
        :props="defaultProps"
        :filter-node-method="filterNode"
        ref="tree">
        <span class="custom-tree-node" slot-scope="{ node, data }" @click="() =>treeClick(data)"> 
          <span>{{ node.label }}</span>
          <span>
            <el-button 
              type="primary" 
              icon="el-icon-edit" circle 
              size="mini" 
              @click="() =>editNode(node,data)"
              style="background-color:rgba(255,255,255,0.15);color:#7C91F5;border:none;margin-left:40px;">
            </el-button>
            <el-dialog title="修改名称" :visible.sync="reName">
              <el-form :model="form">
                <el-form-item label="重命名：" :label-width="formLabelWidth">
                  <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
              </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="reName = false">取 消</el-button>
                <el-button type="primary" @click="() => edit(node,data)">确 定</el-button>
              </div>
            </el-dialog>
            <el-button 
              type="danger" 
              icon="el-icon-delete" circle
              size="mini"
              @click="() => remove(node, data)"
              style="background-color:rgba(255,255,255,0.15);color:#7C91F5;border:none">
            </el-button>
          </span>
        </span>
      </el-tree>
    </el-aside>
    
    <el-container>
      <!-- <el-header style="text-align: left; font-size: 18px;height:40px;line-height:40px" class="el-header">
        <code>接口详情</code>
      </el-header> -->
      
      <el-main>
        <div style="height:100%">
          <el-form ref="form" :model="form" >
            <el-form :inline="true" :model="formInline" class="demo-form-inline" style="width:100%">
              <el-form-item label="环境域名：">
                <el-input placeholder="请输入内容" v-model="formInline.name" clearable style="width:465px"></el-input>
              </el-form-item>
              <el-form-item label="状态：" >
                <el-select v-model="formInline.region" placeholder="活动区域" style="width:465px">
                  <el-option label="未完成" value="unover"></el-option>
                  <el-option label="已完成" value="over"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <el-form-item label="接口路径：">
              <el-input v-model="form.url" placeholder="Request URL" class="input-with-select" style="width:835px">
                <el-select v-model="form.option" slot="prepend" class="el-main-select">
                    <el-option label="get" value="get"></el-option>
                    <el-option label="post" value="post"></el-option>
                </el-select>
              </el-input>
              <el-button type="primary" @click="sendClick()">Send</el-button>
              <el-button type="info" @click="saveClick()">Save</el-button>
            </el-form-item>
            <el-form> 
              <el-form-item label="接口备注：">
                <el-input placeholder="请输入内容" v-model="textarea" style="width:90%"></el-input>
              </el-form-item>
            </el-form>
          </el-form>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="Params" name="first">
              <el-table
                :data="tableData"
                height="250"
                border
                style="width: 100%">
                <el-table-column
                  prop="key"
                  label="key"
                  width="528">
                  <template slot-scope="scope" >
                    <el-input v-model="scope.row.key" size="small"  style="width:100%" @input="clickInput(scope.row.key,scope.$index)"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="value"
                  label="value"
                  width="530">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.value" size="small"  style="width:100%" ></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  fixed="right"
                  label="操作"
                  width="50">
                  <template slot-scope="scope">
                    <el-button
                      @click.native.prevent="deleteRow(scope.$index, tableData)"
                      type="text"
                      size="small">
                      <i class="el-icon-delete"></i>
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="Headers" name="second">
              <el-table
                :data="headerData"
                height="250"
                border
                style="width: 100%">
                <el-table-column
                  prop="key"
                  label="请输入内容"
                  width="528">
                  <template slot-scope="scope" >
                    <el-input v-model="scope.row.key" size="small" style="width:100%" @input="headerInput(scope.row.key,scope.$index)"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="value"
                  label="value"
                  width="530">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.value" size="small"  style="width:100%"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  fixed="right"
                  label="操作"
                  width="50">
                  <template slot-scope="scope">
                    <el-button
                      @click.native.prevent="deleteHeader(scope.$index, headerData)"
                      type="text"
                      size="small">
                      <i class="el-icon-delete"></i>
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="Body" name="third">
              <el-tabs>
                <el-tab-pane name="bodyFirst" label="form">
                  <el-table
                    :data="bodyData"
                    height="250"
                    border
                    style="width: 100%">
                    <el-table-column
                      prop="key"
                      label="key"
                      width="528">
                      <template slot-scope="scopes" >
                        <el-input v-model="scopes.row.key" size="small"  style="width:100%" @input="bodyInput(scopes.row.key,scopes.$index)"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="value"
                      label="value"
                      width="530">
                      <template slot-scope="scopes">
                        <el-input v-model="scopes.row.value" size="small"  style="width:100%" ></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column
                      fixed="right"
                      label="操作"
                      width="50">
                      <template slot-scope="scope">
                        <el-button
                          @click.native.prevent="deleteRow(scope.$index, bodyData)"
                          type="text"
                          size="small">
                          <i class="el-icon-delete"></i>
                        </el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tab-pane>
                <el-tab-pane name="bodySecond" label="raw">
                  <vue-json-editor
                    style="color:black;"
                    v-model="resultInfo"
                    :showBtns="false"    
                    :mode="'code'"  
                    lang="zh"
                    @json-change="onJsonChange" 
                    @json-save="onJsonSave"
                    />
                </el-tab-pane>
              </el-tabs>
            </el-tab-pane>
            <el-tab-pane label="Request-Script" name="fourth">Request-Script</el-tab-pane>
          </el-tabs>
        </div>
      </el-main>
    </el-container>
    <div class="com">
        <el-drawer
          title="Response"
          v-if="table"
          :visible.sync="table"
          direction="rtl"
          append-to-body="true"
          modal="true"
          wrapperClosable="true"
          size="50%">
            <el-tabs>
              <el-tab-pane label="响应信息" name="first">
                <el-tag type="success" size="small" v-model="response">{{response.message}}</el-tag>
                <el-tag type="success" size="small"  v-model="response">响应时间：{{response.time}} ms</el-tag>
                <el-tag type="info" size="small" v-model="response">body长度：{{response.len}}</el-tag>
                <el-collapse v-model="activeNames" @change="handleChange">
                  <el-collapse-item title="body" name="1"  v-model="response">
                    
                  </el-collapse-item>
                  <el-collapse-item title="headers" name="2" v-model="response">
                    
                  </el-collapse-item>
                </el-collapse>
              </el-tab-pane>
              <el-tab-pane label="Headers" name="second">验证结果</el-tab-pane>
              <el-tab-pane label="Body" name="third">异常信息</el-tab-pane>
            </el-tabs>
        </el-drawer>
    </div>
  </el-container>
  
</template>

<style scoped>
.el-header {
  background-color: #b3c0d1;
}
.el-aside {
  color: #333;
}
.editor-container{
  height: 100%;
}
.el-main-select{
  width: 100px;
}
.jsoneditor-poweredBy{
     display: none;
  }

</style>


<script>
  import vueJsonEditor from 'vue-json-editor';
  import api from "@/api/api";

  const list = ["get","post"];

  export default {
    created(){
      api.getAllApiCase().then((response) =>{
        console.log(response)
        this.data =  response.data.all;
      })
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    components: {
      vueJsonEditor
    },
    methods: {
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      treeClick(data){
        if(!data.buttonable){
          api.getApiInfoById(data.id).then(response => {
            console.log(response)
            this.formInline.name=response.data.item.host;
            this.form.url=response.data.item.path;
            this.form.option=list[response.data.item.reqMethod];
            this.textarea=response.data.item.description;
          })
        }
      },
      editNode(node,data){
        this.reName=true;
        this.editdata=data;
      },
      append(data) {
        const newChild = {label: this.form.name,children: [],buttonable:true,value:this.form.name,};
        // if(!this.form.name){
        //   this.dialogFormVisible=false;
        // }
        if (!data.children) {
          this.$set(data, 'children', []);
        }
        data.push(newChild);
        this.apisuite.name=this.form.name;
        api.saveApiSuite(this.apisuite).catch((response)=>{});
        this.dialogFormVisible=false;
        this.form.name="";
      },
      dialogProject(){
        this.dialogFormVisible = false;
        this.form.name="";
      },
      appends(){
        let i;
        const newChild={label:this.form.name,buttonable:false,value:this.form.name};

        for(i=0;i<this.data.length;i++){
          // console.log(this.data[i].label);
          console.log(this.dialogvalue);
          if(this.data[i].label==this.dialogvalue){
            console.log(222)
            this.data[i].children.push(newChild);
          }
        }
        this.dialogFormVisibles=false;
        this.dialogvalue='';
        this.form.name='';
      },
      dialogFile(){
        this.dialogFormVisibles = false;
        this.dialogvalue="";
        this.form.name="";
      },
      edit(node,data){
        let i;
        const parent = node.parent;
        const children = parent.data.children || parent.data;
        const index = children.findIndex(d => d.value === this.editdata.label);
        if(this.editdata.buttonable){
          for(i=0;i<this.data.length;i++){
            if(this.editdata.label==this.data[i].label){
              this.data[i].label=this.form.name;
              this.data[i].value=this.form.name;
            }
          }
        }else{
            children[index].label=this.form.name;
            children[index].value=this.form.name;
        }
        this.reName=false;
        this.form.name='';
        this.editdata='';
      },
      remove(node, data) {
        let i;
        const parent = node.parent;
        const children = parent.data.children || parent.data;
        const index = children.findIndex(d => d.value === data.label);
        if(data.buttonable){
          console.log(data.label);
          for(i=0;i<this.data.length;i++){
            if(data.label==this.data[i].label){
              this.data.splice(i,1);
            }
          }
        }else{
          children.splice(index,1);
        }
      },
      handleClick(tab, event) {
        console.log(tab, event);
      },
      clickInput:function(value,index){
        if(value.length==1&&index==this.tableData.length-1){
          this.tableData.push({
            key:'',
            value:'',
          });
        }
      },
      headerInput:function(value,index){
        if(value.length==1&&index==this.headerData.length-1){
          this.headerData.push({
            key:'',
            value:'',
          });
        }
      },
      bodyInput:function(value,index){
        if(value.length==1&&index==this.bodyData.length-1){
          this.bodyData.push({
            key:'',
            value:'',
          });
        }
      },
      onJsonChange (value) {
            console.log('value:', value);
      },
      onJsonSave (value) {
            console.log('value:', value);
      },
      deleteRow(index, rows) {
        if(rows.length>1){
          rows.splice(index, 1);
        }
      },
      deleteHeader(index, rows){
        if(rows.length>1){
          rows.splice(index, 1);
        }
      },
      handleChange(val) {
        console.log(val);
      },
      sendClick(){
        this.table=true;
      },
      saveClick(){

      }
    },
    data() {
      return {
        data: [
          // {
          // label: '一级 1',
          // buttonable:true,
          // value:'一级 1',
          // children:[{
          //   label:'test',
          //   buttonable:false,
          // }
          // ]
        // }
        ],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        form: {
          name: '',
          region: '',
          url:'',
          option:'',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        apisuite:{
          name:"",
          projectId:-1,
        },
        editableTabs: [{
            title: 'Tab 1',
            name: '1',
            content: 'Tab 1 content'
          }
        ],
        tableData:[{
          key:'',
          value:'',
        }],
        headerData:[{
          key:'',
          value:'',
        }],
        bodyData:[{
          key:'',
          value:'',
        }],
        formInline: {
          name: '',
          region: ''
        },
        resultInfo: {
        },
        response:{
          message:200,
          time:1,
          len:1,
          header:'',
          body:''
        },
        activeNames: ['1'],
        filterText: '',
        tabIndex: 2,
        editableTabsValue: '2',
        activeModel:true,
        dialogFormVisible: false,
        dialogFormVisibles:false,
        reName:false,
        dialogvalue:'',
        labelPosition: 'right',
        formLabelWidth: '120px',
        activeName:"first",
        checked:true,
        labelPosition:"right",
        textarea:'',
        table: false,
        filename:'',
        params:'',
        activename:'projectname',
        editdata:'',
      };
    }
  };
</script>