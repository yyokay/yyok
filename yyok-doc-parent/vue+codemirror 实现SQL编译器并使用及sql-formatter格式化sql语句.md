需求：实现一个SQL编辑区，通过左侧选择自动生成SQL语句。


codemirror代码编辑器使用
安装
npm install --save vue-codemirror

代码：已经封装好的组件，可自行配置参数直接复制使用。（使用的idea主题）
//封装好的组件
<template>
<div class="json-editor">
<textarea ref="textarea" />
</div>
</template>

<script>
	import CodeMirror from 'codemirror'
	import 'codemirror/lib/codemirror.css'
	import 'codemirror/mode/sql/sql.js'
	// 替换主题这里需修改名称
	import 'codemirror/theme/idea.css'
	export default {
		props: {
			value: {
				type: String,
				required: true
			},
			height: {
				type: String,
				required: true
			}
		},
		data() {
			return {
				editor: false
			}
		},
		watch: {
			value(value) {
				const editorValue = this.editor.getValue()
				if (value !== editorValue) {
					this.editor.setValue(this.value)
				}
			},
			height(value) {
				this.editor.setSize('auto', this.height)
			}
		},
		mounted() {
			this.editor = CodeMirror.fromTextArea(this.$refs.textarea, {
				mode: 'text/x-mysql', //语言
				lineNumbers: true, //是否在编辑器左侧显示行号
				lint: true,
				matchBrackets: true, // 括号匹配
				lineWrapping: true,
				tabSize: 2, // 缩进格式
				styleActiveLine: true, // 高亮选中行
				cursorHeight: 0.9,
				// 替换主题这里需修改名称
				theme: 'idea',
				//是否为只读,如果为"nocursor" 不仅仅为只读 连光标都无法在区域聚焦
				readOnly: false,
			})
			this.editor.setSize('auto', this.height)
			this.editor.setValue(this.value)
		},
		methods: {
			getValue() {
				return this.editor.getValue()
			}
		}
	}
</script>

<style scoped>
	.json-editor {
		height: 100%;
	}

	.json-editor>>>.CodeMirror {
		font-size: 14px;
		/* overflow-y:auto; */
		font-weight: normal
	}

	.json-editor>>>.CodeMirror-scroll {}

	.json-editor>>>.cm-s-rubyblue span.cm-string {
		color: #F08047;
	}
</style>

//父组件调用

//绑定value值即可
<code-mirror ref="codemirror" :value="content" :height="'400px'"></code-mirror>
1
2
SQL代码格式化功能
npm安装sql-formatter插件（如果已安装淘宝镜像使用cnpm）
npm install --save sql-formatter
1
引入该sql-formatter.js文件
import sqlFormatter from 'sql-formatter';
1
使用方法
formatSql() {
/*（sql编辑器内容绑定content参数） 将sql内容进行格式后放入编辑器中*/
this.content = sqlFormatter.format(this.content);
},
1
2
3
4
格式化后效果

SQL代码格式化遇到的bug
使用过程中遇到问题：cannot read property ‘format‘ of undefined
解决：版本问题，默认安装4.x.x版本，将版本改低即可。
————————————————

                            版权声明：本文为博主原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接和本声明。

原文链接：https://blog.csdn.net/ka_xingl/article/details/117998681


嗯，同班啊，最好的一个问题。
