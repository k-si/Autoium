<template>
  <el-container style="height:673px; border: 1px solid #eee;">
    <el-aside width="200px" style="background-color: rgb(238,241,246);">
      <el-button type="primary" @click="dialogFormVisible = true" size="mini">新建项目+</el-button>
      <el-dialog title="" :visible.sync="dialogFormVisible">
        <el-tabs v-model="dialogsName" @tab-click="handleClick">
        <el-tab-pane label="创建文件夹" name="newfirst">
          <el-form :model="form">
            <el-form-item label="文件夹名称：" :label-width="formLabelWidth">
              <el-input v-model="form.name" autocomplete="off"  @keyup.enter.native="() => append(data)"></el-input>
            </el-form-item>
          </el-form>
          <div class="dialog-footer" style="float:right">
            <el-button @click="dialogProject()">取 消</el-button>
            <el-button type="primary" @click="() => append(data)">确 定</el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane label="创建文件" name="newsecond">
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
              <el-input v-model="form.name" autocomplete="off" @keyup.enter.native="appends()"></el-input>
            </el-form-item>
          </el-form>
          <div class="dialog-footer" style="float:right">
            <el-button @click="dialogFile()">取 消</el-button>
            <el-button type="primary" @click="appends()">确 定</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
      </el-dialog>
      <el-tree
        class="filter-tree"
        :data="data"
        :props="defaultProps"
        :filter-node-method="filterNode"
        ref="tree">
          <span 
            class="custom-tree-node" 
            slot-scope="{ node, data }" 
            @click="() =>treeClick(data)" 
            @click.stop
            style="display:inline-block;width:200px"
            @mouseenter="onMouseoverEnvDelBtn($event)"
            @mouseleave="onMouseleaveEnvDelBtn($event)"> 
            <span style="display:inline-block">{{ node.label }}</span>
            <span class="env-del-btn-span" style="display:none;float:right;">
              <i 
              class="el-icon-edit"
              size="mini"
              @click="() =>clickEdit(node,data)"
              style="color:#57A6F3"></i>
              <i 
              class="el-icon-delete"
              size="mini"
              @click="() =>clickRemove(node,data)"
              style="color:#57A6F3"></i>
            </span>
            <span>
            </span>
          </span>
      </el-tree>
      <el-dialog title="修改名称" :visible.sync="reName" :modal-append-to-body="false">
        <el-form :model="form">
          <el-form-item label="重命名：" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off" @keyup.enter.native="edit()"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="reName = false">取 消</el-button>
          <el-button type="primary" @click="edit()">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog
      title="提示"
      :visible.sync="dialogVable"
      width="30%"
      :modal-append-to-body="false">
      <span>删除后文件无法回复，是否删除？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVable = false">取 消</el-button>
        <el-button type="primary" @click="remove()">确 定</el-button>
      </span>
</el-dialog>
    </el-aside>
    
    <el-container>
      <el-main>
        <div style="height:100%">
          <el-form ref="form" :model="formInline" >
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
              <el-input v-model="formInline.url" placeholder="Request URL" class="input-with-select" style="width:835px">
                <el-select v-model="formInline.option" slot="prepend" class="el-main-select">
                    <el-option label="get" value="get"></el-option>
                    <el-option label="post" value="post"></el-option>
                </el-select>
              </el-input>
              <el-button type="primary" @click="sendClick()">Send</el-button>
              <el-button type="info" @click="saveClick()">Save</el-button>
            </el-form-item>
            <el-form> 
              <el-form-item label="接口备注：">
                <el-input placeholder="请输入内容" v-model="formInline.textarea" style="width:1000px"></el-input>
              </el-form-item>
            </el-form>
          </el-form>
          <el-form>
            <el-form-item>
              <i class="el-icon-arrow-left" style="float:right;font-size:20px;" @click="responseClick()"></i>
            </el-form-item>
          </el-form>
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="Params" name="first">
              <el-table
                :data="paramsData"
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
                      @click.native.prevent="deleteRow(scope.$index, paramsData)"
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
              <el-tabs v-model="bodyName">
                <el-tab-pane name="bodyFirst" label="form">
                  <el-table
                    :data="bodyForm"
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
                          @click.native.prevent="deleteRow(scope.$index, bodyForm)"
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
                      v-model="bodyJson"
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
            <el-tabs v-model="responseName">
              <el-tab-pane label="响应信息" name="resposefirst">
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
              <el-tab-pane label="Headers" name="responsesecond">验证结果</el-tab-pane>
              <el-tab-pane label="Body" name="responsethird">异常信息</el-tab-pane>
            </el-tabs>
        </el-drawer>
    </div>
  </el-container>
  
</template>

<style scoped>
.el-header {
  background-color: #CBDEF1;
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
            this.formInline.url=response.data.item.path;
            this.formInline.option=list[response.data.item.reqMethod];
            this.formInline.optionnum=response.data.item.reqMethod;
            this.formInline.textarea=response.data.item.description;
          })
          this.formInline.id=data.id;
        }
      },
      append(data) {
        const newChild = {label: this.form.name,children: [],buttonable:true,value:this.form.name,id:""};
        if (!data.children) {
            this.$set(data, 'children', []);
        }
        this.apisuite.name=this.form.name;
        api.saveApiSuite(this.apisuite)
        .then((response) =>{
          newChild.id=response.data.id;
          console.log(response.data.id);
          this.$message({
          type: "success",
          message:"新建成功",
        });
        })
        .catch((response) =>{});
        data.push(newChild);
        this.dialogFormVisible=false;
        this.form.name="";
          
          // this.apisuite.name="";
      },
      dialogProject(){
        this.dialogFormVisible = false;
        this.form.name="";
      },
      appends(){
        let i;
        const newChild={label:this.form.name,buttonable:false,value:this.form.name,id:""};
        if(this.form.name){
          for(i=0;i<this.data.length;i++){
            if(this.data[i].label==this.dialogvalue){
              this.api.name=this.form.name;
              this.api.apiCaseSuiteId=this.data[i].id;
              api.saveApi(this.api)
              .then((response) =>{
                newChild.id=response.data.id;
                this.$message({
                type: "success",
                message:"新建成功",
              });
              })
              .catch((response) =>{});
              this.data[i].children.push(newChild);
            }
          }
        }
        this.dialogFormVisible=false;
        this.dialogvalue='';
        this.form.name='';
      },
      dialogFile(){
        this.dialogFormVisibles = false;
        this.dialogvalue="";
        this.form.name="";
      },
      onMouseoverEnvDelBtn(event) {
          event.target.parentElement.querySelector(".env-del-btn-span").style.cssText += "display:inline-block";
      },
      onMouseleaveEnvDelBtn(event) {
          event.target.parentElement.querySelector(".env-del-btn-span").style.cssText += "display:none"
      },
      clickEdit(node,data){
        let i;
        this.reName=true;
        this.editdata=data;
        const parent = node.parent;
        if(!data.buttonable){
          for(i=0;i<this.data.length;i++){
            if(parent.label==this.data[i].label){
              this.indexparent=i;
              break;
            }
          }
        }
      },
      edit(){
        let i;
        let num = this.indexparent;
        if(this.editdata.buttonable){
          for(i=0;i<this.data.length;i++){
            if(this.editdata.label==this.data[i].label){
              this.data[i].label=this.form.name;
              this.data[i].value=this.form.name;
              this.apisuite.name=this.form.name;
              this.apisuite.id=this.data[i].id;
              api.updateApiSuite(this.apisuite)
              .then((response) =>{
                newChild.id=response.data.id;
                this.$message({
                type: "success",
                message:"修改成功",
              });
              })
              .catch((response) =>{});
              break;
            }
          }
        }else{
          for(i =0;i<this.data[num].children.length;i++){
            console.log(this.data[num].children[i].label);
            if(this.editdata.label==this.data[num].children[i].label){
              this.data[num].children[i].label=this.form.name;
              this.data[num].children[i].value=this.form.name;
              this.api.name=this.form.name;
              this.api.id=this.data[num].children[i].id;
              this.api.apiCaseSuiteId=this.data[num].id;
              api.updateApi(this.api)
              .then((response) =>{
                newChild.id=response.data.id;
                this.$message({
                type: "success",
                message:"修改成功",
              });
              })
              .catch((response) =>{});
              break;
            }
          }
        }
        this.reName=false;
        this.form.name='';
        this.editdata='';
      },
      //提前保存被删除节点的数据，寻找删除节点的位置
      clickRemove(node,data){
        let i;
        this.dialogVable = true;
        this.editdata=data;
        const parent = node.parent;
        if(!data.buttonable){
          for(i=0;i<this.data.length;i++){
            if(parent.label==this.data[i].label){
              this.indexparent=i;
              break;
            }
          }
        }
      },
      remove() {
        let i;
        let num = this.indexparent;
        if(this.editdata.buttonable){
          for(i=0;i<this.data.length;i++){
            if(this.editdata.label==this.data[i].label){
              api.deleteApiSuite(this.data[i].id)
              .then((response) =>{
                this.$message({
                type: "success",
                message:"删除成功"
                })
              })
              .catch((response) =>{});
              this.data.splice(i,1);
              this.editdata='';
              break;
            }
          }
        }else{
          for(i =0;i<this.data[num].children.length;i++){
            console.log(this.data[num].children[i].label);
            if(this.editdata.label==this.data[num].children[i].label){
              api.deleteApi(this.data[num].children[i].id)
              .then((response) =>{
                this.$message({
                type: "success",
                message:"删除成功"
                })
              })
              .catch((response) =>{});
              this.data[num].children.splice(i,1);
              this.editdata='';
              break;
            }
          }
        }
        this.dialogVable=false;
      },
      handleClick(tab, event) {
        // console.log(tab, event);
      },
      clickInput:function(value,index){
        if(value.length>0&&index==this.paramsData.length-1){
          this.paramsData.push({
            key:'',
            value:'',
          });
        }
      },
      headerInput:function(value,index){
        if(value.length>0&&index==this.headerData.length-1){
          this.headerData.push({
            key:'',
            value:'',
          });
        }
      },
      bodyInput:function(value,index){
        if(value.length>0&&index==this.bodyForm.length-1){
          this.bodyForm.push({
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
      responseClick(){
        this.table=true;
      },
      sendClick(){
        this.table=true;
      },
      saveClick(){
        let num;
        if(this.bodyName=='bodyFirst'){
          num=0;
        }else{
          num=1;
        }
        console.log(111);
        const apisuite = {
          id: this.formInline.id,
          host: this.formInline.name,
          path: this.formInline.url,
          reqMethod: this.formInline.optionnum,
          reqHeader:this.headerData,
          reqParams:this.paramsData,
          reqBodyType:num ,
          reqBodyJson:this.bodyJson,
          reqBodyForm:this.bodyForm,
          description: this.formInline.textarea
          };
        api.saveApiDetail(apisuite)
        .then((response) =>{
          this.$message({
          type: "success",
          message:"保存成功",
        });
        })
        .catch((response) =>{});
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
          delivery: false,
          type: [],
          resource: '',
          desc: '',
          id:''
        },
        apisuite:{
          name:'',
          projectId:'-1',
          id:''
        },
        api:{
          name:'',
          projectId:'-1',
          apiCaseSuiteId:'',
          id:''
        },
        editableTabs: [{
            title: 'Tab 1',
            name: '1',
            content: 'Tab 1 content'
          }
        ],
        paramsData:[{
          key:'',
          value:'',
        }],
        headerData:[{
          key:'',
          value:'',
        }],
        bodyForm:[{
          key:'',
          value:'',
        }],
        bodyJson:{},
        formInline: {
          name: '',
          region: '',
          textarea:'',
          url:'',
          option:'',
          id:'',
          optionnum:0
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
        dialogVable:false,
        reName:false,
        dialogvalue:'',
        labelPosition: 'right',
        formLabelWidth: '120px',
        activeName:"first",
        bodyName:'bodyFirst',
        responseName:'resposefirst',
        dialogsName:'newfirst',
        checked:true,
        labelPosition:"right",
        textarea:'',
        table: false,
        filename:'',
        params:'',
        activename:'projectname',
        editdata:'',
        indexparent:0,
        buttonenter:false
      };
    }
  };
</script>