<template>
  <el-container style="border: 1px solid #eee;">
    <!-- 文件侧边栏 -->
    <el-aside width="250px" style="background-color: white;">
      <el-input
        size="small"
        placeholder="输入关键字"
        v-model="filterText"
        prefix-icon="el-icon-search"
        style="margin:5px;margin-right:5px;width:240px">
        <el-button slot="append" icon="el-icon-folder-add" @click="dialogFormVisible = true"></el-button>
      </el-input>
      
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
              <el-button @click="dialogProject()">取 消</el-button>
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
            @click="() =>treeClick(data,node)" 
            @click.stop
            style="display:block;width:300px;font-size:15px;"
            @mouseenter="onMouseoverEnvDelBtn($event)"
            @mouseleave="onMouseleaveEnvDelBtn($event)"> 
              <span style="display:inline-block;height:30px;line-height:30px;overflow:hidden;">
                <span style="display:inline-block;height:30px;line-height:30px;overflow:hidden;">
                  <i class="el-icon-folder-opened" size="mini" v-show="data.buttonable"></i>
                  <el-tag type="warning" v-show="!data.buttonable" v-if="data.method==1" size="mini">POST</el-tag>
                  <el-tag type="success" v-show="!data.buttonable" v-else size="mini">GET</el-tag>
                </span>
                <span style="display:inline-block;height:30px;line-height:30px">
                  <div 
                    style="text-overflow:ellipsis;
                    overflow:hidden;
                    white-space:nowrap;
                    width:100px;">
                    <span>
                      {{ node.label }}
                    </span>
                  </div>
                </span>
                
              </span>
              <span 
              class="env-del-btn-span" 
              style="display:none;float:right;margin-right:10px;line-height:30px">
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
    
    <!-- 初始页面 -->
    <el-container 
      style="background-color:#DCDFE6;height:100%;text-align:center;" 
      v-if="containerShow">
      <el-main style="background-color:white;margin-left:1px;">
        <div class="block" style="height:100%">
          <el-image :src="src" style="top:30%;"></el-image>
        </div>
      </el-main>
    </el-container>

    <!-- 文件详细信息 -->
    <el-container style="background-color:#DCDFE6;height:100%" v-if="containerMain">
      <el-main style="background-color:white;margin-left:1px">
        <div style="height:100%">
          <!-- 右上部分 -->
          <el-form ref="form" :model="formInline" style="width:100%">
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
              <el-form-item label="环境域名：" style="width:45%">
                <el-input placeholder="请输入内容" v-model="formInline.name" clearable ></el-input>
              </el-form-item>
              <el-form-item label="状态："  style="width:45%">
                <el-select v-model="formInline.region" placeholder="活动区域">
                  <el-option label="未完成" value="unover"></el-option>
                  <el-option label="成功" value="success"></el-option>
                  <el-option label="失败" value="fail"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <el-form-item label="接口路径：" style="width:100%">
              <el-input v-model="formInline.url" placeholder="Request URL" class="input-with-select" style="width:70%" @input="clickUrl()">
                <el-select v-model="formInline.option" slot="prepend" class="el-main-select">
                    <el-option label="GET" value="get"></el-option>
                    <el-option label="POST" value="post"></el-option>
                </el-select>
              </el-input>
              <el-button type="primary" @click="sendClick()">Send</el-button>
              <el-button type="info" @click="saveClick()">Save</el-button>
            </el-form-item>
            <el-form style="width:100%"> 
              <el-form-item label="接口备注：">
                <el-input placeholder="请输入内容" v-model="formInline.textarea" style="width:87%"></el-input>
              </el-form-item>
            </el-form>
          </el-form>
          <!-- send后拉取显示响应值 -->
          <el-form v-show="responseAble">
            <el-form-item>
              <i class="el-icon-arrow-left" style="float:right;font-size:20px;" @click="responseClick()"></i>
            </el-form-item>
          </el-form>
          <!-- 右下部分 -->
          <el-tabs v-model="activeName" @tab-click="handleClick" style="width:100%">
            <el-tab-pane label="Params" name="first">
              <el-table
                :data="paramsData"
                border
                height="400"
                style="width: 100%">
                <el-table-column
                  prop="key"
                  label="key"
                  style="width:45%"
                  align="center">
                  <template slot-scope="scope" >
                    <el-input v-model="scope.row.key" size="small"  style="width:100%" @focus="inputOn(scope.row.key,scope.$index)" @input="clickInputKey(scope.row.key,scope.$index)"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="value"
                  label="value"
                  style="width:45%"
                  align="center">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.value" size="small"  style="width:100%" @focus="inputOn(scope.row.key,scope.$index)" @input="clickInputValue(scope.row.key,scope.$index)"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="50"
                  align="center">
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
                height="400"
                border
                style="width: 100%">
                <el-table-column
                  prop="key"
                  label="请输入内容"
                  style="width:45%"
                  align="center">
                  <template slot-scope="scope" >
                    <el-input v-model="scope.row.key" size="small" style="width:100%" @input="headerInput(scope.row.key,scope.$index)"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="value"
                  label="value"
                  style="width:45%"
                  align="center">
                  <template slot-scope="scope">
                    <el-input v-model="scope.row.value" size="small"  style="width:100%"></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="50"
                  align="center">
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
                    height="350"
                    border
                    style="width: 100%">
                    <el-table-column
                      prop="key"
                      label="key"
                      style="width:45%"
                      align="center">
                      <template slot-scope="scopes" >
                        <el-input v-model="scopes.row.key" size="small"  style="width:100%" @input="bodyInput(scopes.row.key,scopes.$index)"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column
                      prop="value"
                      label="value"
                      style="width:45%"
                      align="center">
                      <template slot-scope="scopes">
                        <el-input v-model="scopes.row.value" size="small"  style="width:100%" ></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="操作"
                      width="50"
                      align="center">
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
                      style="color:black;height:350px"
                      v-model="bodyJson"
                      :showBtns="false"    
                      :mode="'code'" 
                      :key="formInline.name" 
                      lang="ch"
                      @json-change="onJsonChange" 
                      @json-save="onJsonSave"/>
                      <!-- <div>
                        <vue-json-editor 
                          v-model="bodyJson" 
                          :show-btns="true" 
                          :mode="'code'" 
                          :key="formInline.name" 
                          :expandedOnStart="true" 
                          @json-change="onJsonChange">
                        </vue-json-editor>
                      </div> -->
                </el-tab-pane>
              </el-tabs>
            </el-tab-pane>
            <el-tab-pane label="Assertion" name="fourth">
              <el-table
                :data="assertionData"
                height="400"
                border
                style="width: 100%">
                <el-table-column
                  prop="data"
                  label="数据源"
                  style="width: 10%"
                  align="center">
                  <template slot-scope="scopef" slot="header">
                    数据源
                    <el-button type="text" @click="appendEditor(scopef.$index)">
                      <i class="el-icon-circle-plus-outline" ></i>
                    </el-button>
                  </template>
                  <template slot-scope="scopet" >
                    <el-select v-model="scopet.row.data" placeholder="请选择">
                      <el-option
                        v-for="item in datasourceOption"
                        :key="item.key"
                        :label="item.value"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="express"
                  label="提取表达式"
                  style="width: 10%"
                  align="center">
                  <template slot-scope="scopet">
                    <el-input v-model="scopet.row.express" size="small"  style="width:100%" ></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="relation"
                  label="期望关系"
                  style="width: 10%"
                  align="center">
                  <template slot-scope="scopet" >
                    <el-select v-model="scopet.row.relation" placeholder="请选择">
                      <el-option
                        v-for="(item,index) in relationshipOption"
                        :key="index"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="type"
                  label="期望类型"
                  style="width: 10%"
                  align="center">
                  <template slot-scope="scopet" >
                    <el-select v-model="scopet.row.type" placeholder="请选择">
                      <el-option
                        v-for="item in typeOption"
                        :key="item.key"
                        :label="item.value"
                        :value="item.value">
                      </el-option>
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="expectValue"
                  label="期望值"
                  style="width:19%"
                  align="center">
                  <template slot-scope="scopet">
                    <el-input v-model="scopet.row.expectValue" size="small"  style="width:100%" ></el-input>
                  </template>
                </el-table-column>
                <el-table-column
                  label="操作"
                  width="50"
                  align="center">
                  <template slot-scope="scopet">
                    <el-button
                      @click.native.prevent="deleteAssertion(scopet.$index, assertionData)"
                      type="text"
                      size="small">
                      <i class="el-icon-delete"></i>
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane> 
            <el-tab-pane label="Request-Script" name="fifth">Request-Script</el-tab-pane>
          </el-tabs>
        </div>
      </el-main>
    </el-container>

    <!-- 响应部分 -->
    <div class="com">
        <el-drawer
          title="Response"
          v-if="table"
          :visible.sync="table"
          direction="rtl"
          :append-to-body="true"
          :modal="true"
          :wrapperClosable="true"
          size="70%">
            <el-tabs v-model="responseName" style="height:100%;margin-left:10px;">
              <el-tab-pane label="响应信息" name="resposefirst">
                <el-tag :type="response.type" disabled size="small" v-model="response">{{response.message}}</el-tag>
                <el-tag :type="response.type" size="small"  v-model="response">响应时间：{{response.time}} ms</el-tag>
                <el-tag type="info" size="small" v-model="response">body长度：{{response.len}}</el-tag>
                <el-collapse v-model="activeNames" @change="handleChange">
                  <el-collapse-item title="headers" name="1" v-model="response">
                    <el-col :span="20">
                      <el-col
                        :span="20"
                        v-for="(item, index) in response.header"
                        :key="index">
                        <el-row>
                          <el-col :span="10" style="font-weight: bold;">
                            {{ item.key }}：
                          </el-col>
                          <el-col :span="10">
                            {{ item.value }}
                          </el-col>
                        </el-row>
                        
                      </el-col>
                    </el-col>
                  </el-collapse-item>
                  <el-collapse-item 
                  title="body" 
                  name="2"  
                  v-model="response">
                  <el-tabs v-model="responsebodyName" style="height:100%;margin-left:10px;">
                    <el-tab-pane label="Pretty" name="resposebodyfirst">
                      <div style="overflow-y:scroll;height:490px">
                        <span style="font-size:14px;color: #686868;"  v-text="response.body">
                          {{response.body}} 
                        </span>
                      </div>
                    </el-tab-pane>
                    <el-tab-pane label="Preview" name="resposebodysecond">
                      <div style="overflow-y:scroll;height:490px">
                        <span style="font-size:14px;color: #686868;"  v-html="response.body">
                          <!-- {{response.body}}  -->
                        </span>
                      </div>
                    </el-tab-pane>
                  </el-tabs>
                  </el-collapse-item>
                </el-collapse>
              </el-tab-pane>
              <el-tab-pane label="验证结果" name="responsesecond">
                <el-table
                  :data="response.assertion"
                  border
                  style="width: 100%">
                  <el-table-column
                    prop="data"
                    label="数据源"
                    width="200"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="express"
                    label="提取表达式"
                    width="200"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="relation"
                    label="期望关系"
                    width="150"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="type"
                    label="期望类型"
                    width="80"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="expectValue"
                    label="期望值"
                    width="120"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="realTypeText"
                    label="实际类型"
                    width="80"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="realValue"
                    label="实际值"
                    width="120"
                    align="center">
                  </el-table-column>
                  <el-table-column
                    prop="result"
                    label="验证结果"
                    width="115"
                    align="center">
                    <template slot-scope="scope">
                      <el-tag type="success" v-if="scope.row.result==='true'" size="small">true</el-tag>
                      <el-tag type="danger" v-else size="small">false</el-tag>
                    </template>
                  </el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="异常信息" name="responsethird">
                <div style="overflow-y:scroll;height:490px">
                  <span style="font-size:14px;color: #686868;"  v-text="response.exception">
                    {{response.exception}} 
                  </span>
                </div>
              </el-tab-pane>
            </el-tabs>
        </el-drawer>
    </div>
  </el-container>
  
</template>

<style>
.el-header {
  background-color: white;
}
.el-aside {
  color: #333;
}
.el-container{
  height: calc(100vh - 50px);
  padding:0;
  margin:0;
  width:100%;
}
.el-main-select{
  width: 100px;
}
.jsoneditor-poweredBy{
     display: none;
}

.el-form--inline .el-form-item__content {
    width: 350px;
}

.jsoneditor-vue{
  height:100%;
}
div.jsoneditor-outer {
    position: static;
    width: 100%;
    height: 100%;
    margin: 0px;
    padding: 0px;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}
/* .ace-jsoneditor .ace_scroller {
    background-color: white;
}
.ace-jsoneditor .ace_gutter {
    background:  #2D4969;
    color: #8896A6;
}
.ace-jsoneditor .ace_gutter-active-line {
    background-color: #8896A6;
}
.ace-jsoneditor .ace_marker-layer .ace_active-line {
    background: #F2F6FC;
} */
div.jsoneditor-menu {
    display: none;
}
div.jsoneditor {
    border: 1px solid #F2F6FC;
}

</style>


<script>
  import vueJsonEditor from 'vue-json-editor';
  import api from "@/api/api";

  const listoption = ["get","post"];
  const listype=["bodySecond","bodyFirst"];
  const liststatus=["unover","success","fail"];


  export default {
    // 当显示此页面时，获取所有的文件夹/文件并显示
    created(){
      api.getAllApiCase().then((response) =>{
        this.data =  response.data.all;
      })
      this.containerShow=true;
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
      // 树形控件每个子节点自带的操作
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 点击文件获取到每个文件对应的formInline
      treeClick(data,node){
        this.responseAble=false;
        if(!data.buttonable){
          this.activeName="first";
          this.responseName='resposefirst';
          this.dialogsName='newfirst';
          this.responsebodyName="resposebodyfirst";
          const newChild = {key:'',value:''};
          this.formInline={
            name: '',
            region: '',
            textarea:'',
            url:'',
            option:'',
            statusCode:0,
            id:'',
            optionnum:0,
            bodynum:0,
            len:0
          };
          this.paramsData=[];
          this.headerData=[];
          this.bodyForm=[];
          this.bodyJson=[];
          let i;
          let j;
          let lis="";
          var array=[];
          var strs=new Array();
          var str=[];
          var paramsdata=[];
          this.params=[];
          this.response={
            message:200,
            time:1,
            len:1,
            header:[],
            body:'',
            type:"success"
          };
          this.containerShow=false;
          this.containerMain=true;
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
          api.getApiInfoById(data.id).then(response => {
            this.formInline.name=response.data.item.host;
            this.formInline.url=response.data.item.path;
            this.formInline.option=listoption[response.data.item.reqMethod];
            this.formInline.optionnum=response.data.item.reqMethod;
            this.formInline.textarea=response.data.item.description;
            this.formInline.statusCode=response.data.item.statusCode;
            this.formInline.bodynum=response.data.item.reqBodyType;
            this.formInline.statusCode=response.data.item.finish;
            this.formInline.region=liststatus[this.formInline.statusCode];
            this.bodyName=listype[this.formInline.bodynum];
            if(response.data.item.examine==null){
              this.assertionData=[];
            }else{
              this.assertionData=response.data.item.examine;
            }
            for(i=0;i<response.data.item.path.length;i++){
              if(response.data.item.path[i]=="?"){
                lis=response.data.item.path.substring(i+1);
                strs=lis.split("&"); 
                for(j=0;j<strs.length;j++){
                  str=strs[j].split("=");
                  array.push(str[0]);
                  array.push(str[1]);
                }
                this.params=array;
                for(j=0;j<array.length/2;j++){
                  const newChild = {key:'',value:''};
                  newChild.key=array[2*j];
                  newChild.value=array[2*j+1];
                  paramsdata.push(newChild);
                }
              }
            }
            this.paramsData=paramsdata;
            this.paramsData.push({key:'',value:''});
            if(response.data.item.reqHeader!=null){
              this.headerData=response.data.item.reqHeader;
            }else{
              this.headerData=[{key:'',value:''}];
            }
            if(response.data.item.reqBodyForm!=null){
              this.bodyForm=response.data.item.reqBodyForm;
            }else{
              this.bodyForm=[{key:'',value:''}];
            }
            if(JSON.parse(response.data.item.reqBodyJson)==null){
              this.bodyJson=[];
            }else{
              this.bodyJson=JSON.parse(response.data.item.reqBodyJson);
            }
            })
          this.formInline.id=data.id;
          
        }
      },
      // 增加文件夹（点击弹窗的确定后执行）
      append(data) {
        const newChild = {label: this.form.name,children: [],buttonable:true,value:this.form.name,id:""};
        if (!data.children) {
            this.$set(data, 'children', []);
        }
        this.apisuite.name=this.form.name;
        if(this.form.name.length>19){
          this.$message({
          message: '名称字长超出限制！',
          type: 'warning'
        });
        }else{
          api.saveApiSuite(this.apisuite)
          .then((response) =>{
            newChild.id=response.data.id;
            this.$message({
            type: "success",
            message:"新建成功",
          });
          })
          .catch((response) =>{});
          if(this.form.name.length>0){
            data.push(newChild);
          }
        }
        this.dialogFormVisible=false;
        this.form.name="";
      },
      //单击取消时弹窗关闭
      dialogProject(){
        this.dialogFormVisible = false;
        this.dialogsName="newfirst";
        this.form.name="";
      },
      // 增加文件到对应文件夹
      appends(){
        let i;
        const newChild={label:this.form.name,buttonable:false,value:this.form.name,id:"",method:0};
        if(this.form.name.length>19){
          this.$message({
          message: '名称字长超出限制！',
          type: 'warning'
        });
        }else{
          for(i=0;i<this.data.length;i++){
            if(this.data[i].label==this.dialogvalue){
              this.api.name=this.form.name;
              this.api.apiCaseSuiteId=this.data[i].id;
              if(this.form.name.length>0){
                this.data[i].children.push(newChild);
              }
              break;
            }
          }
          api.saveApi(this.api)
            .then((response) =>{
              newChild.id=response.data.id;
              this.$message({
              type: "success",
              message:"新建成功",
            });
          })
          .catch((response) =>{});
        }
        this.dialogFormVisible=false;
        this.dialogvalue='';
        this.dialogsName="newfirst";
        this.form.name='';
      },
      /*已弃用的点击弹出弹窗方法
      dialogFile(){
        this.dialogFormVisibles = false;
        this.dialogvalue="";
        this.form.name="";
      },*/

      // 单击加号增加一个assertion
      appendEditor(index){
        const newChild = {
          dataSource:'',
          data:'',
          express:'',
          expectRelation:'',
          relation:'',
          type:'',
          expectType:'',
          expectValue:'',
          actualtype:'',
          actualvalue:'',
          detectionresult:''
        };
        this.assertionData.push(newChild);
      },
      // 鼠标移入显示修改与删除图标，移除图标消失
      onMouseoverEnvDelBtn(event) {
          event.target.parentElement.querySelector(".env-del-btn-span").style.cssText += "display:inline-block";
      },
      onMouseleaveEnvDelBtn(event) {
          event.target.parentElement.querySelector(".env-del-btn-span").style.cssText += "display:none"
      },
      // 点击修改图标
      clickEdit(node,data){
        let i;
        this.reName=true;
        const parent = node.parent;
        this.form.name=data.label;
        if(!data.buttonable){
          this.editdata=data;
          for(i=0;i<this.data.length;i++){
            if(parent.label==this.data[i].label){
              this.indexparent=i;
              break;
            }
          }
        }else{
          this.editdatas=data;
        }
      },
      // 对文件夹/文件进行名称修改
      edit(){
        let i;
        let num = this.indexparent;
        if(this.form.name.length>19){
          this.$message({
            message: '名称字长超出限制！',
            type: 'warning'
          });
        }else{
          if(this.editdatas.buttonable){
          for(i=0;i<this.data.length;i++){
            if(this.editdatas.label==this.data[i].label){
              if(this.form.name.length>0){
                this.data[i].label=this.form.name;
                this.data[i].value=this.form.name;
              }
              this.apisuite.name=this.form.name;
              this.apisuite.id=this.data[i].id;
              api.updateApiSuite(this.apisuite)
              .then((response) =>{
                newChild.id=response.data.id;
              })
              .catch((response) =>{});
              this.$message({
                type: "success",
                message:"修改成功",
              });
              break;
            }
          }
        }else{
          for(i =0;i<this.data[num].children.length;i++){
            if(this.editdata.label==this.data[num].children[i].label){
              if(this.form.name.length>0){
                this.data[num].children[i].label=this.form.name;
                this.data[num].children[i].value=this.form.name;
              }
              this.api.name=this.form.name;
              this.api.id=this.data[num].children[i].id;
              this.api.apiCaseSuiteId=this.data[num].id;
              api.updateApi(this.api)
              .then((response) =>{
                newChild.id=response.data.id;
              })
              .catch((response) =>{});
              this.$message({
                type: "success",
                message:"修改成功",
              });
              break;
            }
          }
        }
        }   
        this.reName=false;
        this.form.name='';
        this.editdatas='';
      },
      //点击节点触发，提前保存被删除节点的数据，寻找删除节点的位置
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
      // 删除文件夹/文件
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
      // 更改当前页头
      handleClick(tab, event) {
        // console.log(tab, event);
      },
      // 输入URL时与header进行交互
      clickUrl(){
        var list=this.formInline.url;
        var strs = new Array();
        let i;
        let j;
        var str = new Array();
        var array=[];
        for(i=0;i<this.params.length;i++){
          array[i]=this.params[i];
        }
        for(i=0;i<this.formInline.url.length;i++){
          if(this.formInline.url[i]=="?"){
            list=list.substring(i+1);
            strs=list.split("&"); 
            for(j=0;j<strs.length;j++){
              str=strs[j].split("=");
              array.splice(j*2,1,str[0]);
              array.splice(j*2+1,1,str[1]);
            }
            if(strs.length*2<array.length){
              array.splice(strs.length*2,array.length);
            }
            this.paramsData=[];
            for(j=0;j<array.length/2;j++){
              const newChild = {key:'',value:''};
              newChild.key=array[2*j];
              newChild.value=array[2*j+1];
              this.paramsData.push(newChild);
            }
            this.params=array;
            this.paramsData.push({key:"",value:""});
            break;
          }else if(i==this.formInline.url.length-1&&this.formInline.url[i]!="?"){
            this.paramsData=[{key:"",value:""}];
          }
        }
        if(this.formInline.url.length==0){
          this.paramsData=[{key:"",value:""}];
        }
        
      },
      // 在params部分输入key/value时对URL也进行修改
      clickInputKey:function(value,index){
        let i;
        var array= new Array();
        let list=this.formInline.url;
        for(i=0;i<this.formInline.url.length;i++){
          if(this.formInline.url[i]=="?"){
            list=list.substring(0,i);
            break;
          }
        }
        for(i=0;i<this.params.length;i++){
          array[i]=this.params[i];
        }
        if(value.length>0&&index==this.paramsData.length-1){
          this.paramsData.push({
            key:'',
            value:'',
          });
        }
        if(index==0){
          var l = this.paramsData[index].key;
          array.splice(2*index,1,l);
        }else{
          const l =this.paramsData[index].key;
          array.splice(2*index,1,l);
        }
        for(i=0;i<array.length;i++){
          if(i==0){
            list+="?";
            list+=array[i];
          }else if(i%2==0){
            list+="&";
            list+=array[i];
          }else{
            list+="=";
            list+=array[i];
          }
        }
        this.formInline.url=list;
        this.params=array;
        this.formInline.len=this.paramsData[index].key.length;
      },
      clickInputValue:function(value,index){
        let i;
        var array=[];
        let list=this.formInline.url;
        for(i=0;i<this.formInline.url.length;i++){
          if(this.formInline.url[i]=="?"){
            list=list.substring(0,i);
            break;
          }
        }
        for(i=0;i<this.params.length;i++){
          array[i]=this.params[i];
        }
        const l =this.paramsData[index].value;
        array.splice((2*index+1),1,l);
        for(i=0;i<array.length;i++){
          if(i==0){
            list+="?";
            if(this.paramsData[index].key.length==0&&index*2==i){
              list+="";
            }else{
              list+=array[i];
            }
          }else if(i%2==0){
            list+="&";
            if(this.paramsData[index].key.length==0&&index*2==i){
              list+="";
            }else{
              list+=array[i];
            }
          }else{
            list+="=";
            list+=array[i];
          }
        }
        this.formInline.url=list;
        this.params=array;
        this.formInline.len=this.paramsData[index].value.length;
      },
      // 在params中删除数据URL也对应更改
      deleteRow(index, rows) {
        if(rows.length>1){
          rows.splice(index, 1);
        }
        if(this.activeName=="first"){
          this.params.splice(index*2,2);
          var array= new Array();
          let i;
          let list=this.formInline.url;
          for(i=0;i<this.formInline.url.length;i++){
            if(this.formInline.url[i]=="?"){
              list=list.substring(0,i);
              break;
            }
          }
          for(i=0;i<this.params.length;i++){
            array[i]=this.params[i];
          }
          for(i=0;i<array.length;i++){
            if(i==0){
              list+="?";
              list+=array[i];
            }else if(i%2==0){
              list+="&";
              list+=array[i];
            }else{
              list+="=";
              list+=array[i];
            }
          }
          this.formInline.url=list;
          this.params=array;
        }
      },
      // 当输入框获取焦点时
      inputOn:function(value,index){
        this.formInline.len=value.length; 
      },
      // header部分输入自动添加一行数据
      headerInput:function(value,index){
        if(value.length>0&&index==this.headerData.length-1){
          this.headerData.push({
            key:'',
            value:'',
          });
        }
      },
      // body的form部分输入自动添加一行数据
      bodyInput:function(value,index){
        if(value.length>0&&index==this.bodyForm.length-1){
          this.bodyForm.push({
            key:'',
            value:'',
          });
        }
      },
      // 对body的json输入框的操作(暂未使用)
      onJsonChange (value) {
            // console.log('value:', value);
      },
      onJsonSave (value) {
            // console.log('value:', value);
      },
      // 在header中的删除操作
      deleteHeader(index, rows){
        if(rows.length>1){
          rows.splice(index, 1);
        }
      },
      // 在assertion中的删除操作
      deleteAssertion(index, rows){
        rows.splice(index, 1);
      },
      handleChange(val) {
        // console.log(val);
      },
      responseClick(){
        this.table=true;
      },
      // 点击send后返回响应值
      sendClick(){
        let num;
        let i;
        let j;
        const loading = this.$loading({
          lock: true,
          text: 'Loading',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        if(this.bodyName=="bodySecond"){
          num=0;
        }else{
          num=1;
        }
        if(this.formInline.option=="get"){
          this.requesttype="get";
          this.labeltype="success";
        }else{
          this.requesttype="post";
          this.labeltype="warning";
        }
        for(i=0;this.assertionData!=null&&i<this.assertionData.length;i++){
          for(j=0;j<this.datasourceOption.length;j++){
            if(this.assertionData[i].data==this.datasourceOption[j].value){
              this.assertionData[i].dataSource=j;
            }
          }
          for(j=0;j<this.relationshipOption.length;j++){
            if(this.assertionData[i].relation==this.relationshipOption[j].value){
              this.assertionData[i].expectRelation=j;
            }
          }
          for(j=0;j<this.typeOption.length;j++){
            if(this.assertionData[i].type==this.typeOption[j].value){
              this.assertionData[i].expectType=j;
            }
          }
        }
        const apisuite = {
          host: this.formInline.name,
          path: this.formInline.url,
          id:this.formInline.id,
          reqMethod: this.formInline.optionnum,
          reqHeader:this.headerData,
          reqParams:this.paramsData,
          reqBodyType:num ,
          reqBodyJson:JSON.stringify(this.bodyJson),
          reqBodyForm:this.bodyForm,
          examine:this.assertionData,
          description:this.formInline.textarea
        };
        api.sendApi(apisuite)
        .then((response)=>{
          this.response.message=response.data.res.statusCode;
          this.response.len=response.data.res.respBodySize;
          this.response.body=response.data.res.body;
          this.response.header=response.data.res.headers;
          this.response.assertion=response.data.res.assertResult;
          this.response.exception=response.data.res.exception;
          this.formInline.statusCode=response.data.res.finish;
          this.formInline.region=liststatus[this.formInline.statusCode];
          if(this.response.exception==null){
            this.response.exception='No Error';
          }
          if(this.response.message=="200"){
            this.response.type='success';
          }else{
            this.response.type='danger';
          }
        })
        .catch((response)=>{});
        setTimeout(() => {
          loading.close();
          this.table=true;
          this.responseAble=true;
        }, 500);
      },
      // 保存每个文件的相关数据
      saveClick(){
        let num;
        let i;
        let j;
        let nums = this.indexparent;
        for(i=0;this.assertionData!=null&&  i<this.assertionData.length;i++){
          for(j=0;j<this.datasourceOption.length;j++){
            if(this.assertionData[i].data==this.datasourceOption[j].value){
              this.assertionData[i].dataSource=j;
            }
          }
          for(j=0;j<this.relationshipOption.length;j++){
            if(this.assertionData[i].relation==this.relationshipOption[j].value){
              this.assertionData[i].expectRelation=j;
            }
          }
          for(j=0;j<this.typeOption.length;j++){
            if(this.assertionData[i].type==this.typeOption[j].value){
              this.assertionData[i].expectType=j;
            }
          }
        }
        if(this.bodyName=="bodySecond"){
          num=0;
        }else{
          num=1;
        }
        if(this.formInline.option=="get"){
          this.requesttype="get";
          this.labeltype="success";
        }else{
          this.requesttype="post";
          this.labeltype="warning";
        }
        for(i=0;i<listoption.length;i++){
          if(this.formInline.option==listoption[i]){
            this.formInline.optionnum=i;
            break;
          }
        }
        for(i=0;i<liststatus.length;i++){
          if(this.formInline.region==liststatus[i]){
            this.formInline.statusCode=i;
          }
        }
        const apisuite = {
          id: this.formInline.id,
          host: this.formInline.name,
          path: this.formInline.url,
          reqMethod: this.formInline.optionnum,
          reqHeader:this.headerData,
          reqParams:this.paramsData,
          reqBodyType:num ,
          reqBodyJson:JSON.stringify(this.bodyJson),
          reqBodyForm:this.bodyForm,
          examine:this.assertionData,
          finish:this.formInline.statusCode,
          description: this.formInline.textarea
        };
        this.api.method=this.formInline.optionnum;
        api.saveApiDetail(apisuite)
        .then((response) =>{
          this.$message({
          type: "success",
          message:"保存成功",
        });
        })
        .catch((response) =>{});
        for(i =0;i<this.data[nums].children.length;i++){
            if(this.editdata.label==this.data[nums].children[i].label){
              this.data[nums].children[i].method=this.formInline.optionnum;
              break;
            }
        }
      }
    },
    data() {
      return {
        data: [
          
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
          id:'',
          method:0
        },
        editableTabs: [{
            title: 'Tab 1',
            name: '1',
            content: 'Tab 1 content'
          }
        ],
        params:[],
        paramsData:[{key:'',value:''}],
        headerData:[{key:'',value:''}],
        bodyForm:[{key:'',value:''}],
        bodyJson:'',
        assertionData:[],
        datasourceOption:[{
          label:'响应码',
          value:'响应码'
        },{
          label:'响应头',
          value:'响应头'
        },{
          label:'响应体（JSON）',
          value:'响应体（JSON）'
        },{
          label:'响应体（XML）',
          value:'响应体（XML）'
        },{
          label:'响应体（TEXT）',
          value:'响应体（TEXT）'
        }],
        relationshipOption:[{
          label:'等于',
          value:'等于'
        },{
          label:'大于',
          value:'大于'
        },{
          label:'大于等于',
          value:'大于等于'
        },{
          label:'小于',
          value:'小于'
        },{
          label:'小于等于',
          value:'小于等于'
        },{
          label:'包含',
          value:'包含'
        },{
          label:'不包含',
          value:'不包含'
        },{
          label:'长度等于',
          value:'长度等于'
        },{
          label:'长度大于',
          value:'长度大于'
        },{
          label:'长度大于等于',
          value:'长度大于等于'
        },{
          label:'长度小于',
          value:'长度小于'
        },{
          label:'长度小于等于',
          value:'长度小于等于'
        },{
          label:'开始于',
          value:'开始于'
        },{
          label:'结束于',
          value:'结束于'
        }],
        typeOption:[{
          label:'Null',
          value:'Null'
        },{
          label:'数值',
          value:'数值'
        },{
          label:'字符串',
          value:'字符串'
        },{
          label:'布尔值',
          value:'布尔值'
        }],
        formInline: {
          name: '',
          region: '',
          textarea:'',
          url:'',
          option:'',
          id:'',
          optionnum:0,
          bodynum:0,
          len:0
        },
        response:{
          message:0,
          time:1,
          len:1,
          header:[],
          body:'',
          type:"success",
          exception:'',
          assertion:''
        },
        activeModel:true,
        dialogFormVisible: false,
        dialogVable:false,
        reName:false,
        table: false,
        checked:true,
        buttonenter:false,
        containerShow:false,
        containerMain:false,
        responseAble:false,
        src:'static/kong.png',
        activeNames: ['1'],
        filterText: '',
        editableTabsValue: '2',
        dialogvalue:'',
        labelPosition: 'right',
        formLabelWidth: '120px',
        activeName:"first",
        bodyName:'bodyFirst',
        responseName:'resposefirst',
        dialogsName:'newfirst',
        responsebodyName:"resposebodyfirst",
        labelPosition:"right",
        textarea:'',
        filename:'',
        params:'',
        activename:'projectname',
        editdata:'',
        editdatas:'',
        requesttype:'get',
        tabIndex: 2,
        indexparent:0,
        keys:1
      };
    }
  };
</script>