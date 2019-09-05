/**
 *     加入节点长度的属性  length 
 *     加入checkbox的索引与其值的映射   map
 *     update date 2009-06-05
 *     add reload function 2010-05-01
**/

function Node(id, pid, name, url, onclick, checked,value,title, target, icon, iconOpen, open) {

	this.id = id;

	this.pid = pid;

	this.name = name;

	this.url = url;
	this.onclick = onclick;

	this.checked =checked||false;
	this.title = title;

	this.target = target;

	this.icon = icon;

	this.iconOpen = iconOpen;

	this._io = open || false;

	this._is = false;

	this._ls = false;

	this._hc = false;

	this._ai = 0;

	this._p;
	this._checked = checked;
	this.value = value;
};
//Compare to nodes.

function nodeCompare(node1, node2)
{
	if(node1.pid*1<node2.pid*1){
		return -1;
	}else if(node1.pid*1==node2.pid*1){
		if(node1.id*1 <node2.id*1){
			return -1;
		}else if(node1.id*1==node2.id*1){
			return 0;
		}else{
			return 1
		}
	}else{
		return 1;
	}
}


// Tree object

function myTree(objName) {

	this.config = {

		target					: null,

		folderLinks				: false,

		useSelection			: true,

		useCookies				: true,

		useLines				: true,

		useIcons				: false,

		useStatusText			: true,

		closeSameLevel			: false,

		inOrder					: false
,
		treeType				: null,
		sort					: true,
		disabled				: false,
		pNodeClass				: "myTreeNode",
		hideRoot                : false
	}

	this.icon = {
		path				: 'img/',

		root				: 'base.gif',          
		folder				: 'folder.gif',        
		folderOpen			: 'folderopen.gif',    
		node				: 'page.gif',          
		empty				: 'empty.gif',         
		line				: 'line.gif',          
		join				: 'join.gif',          
		joinBottom			: 'joinbottom.gif',    
		plus				: 'plus.gif',          
		plusBottom		    : 'plusbottom.gif',    
		minus				: 'minus.gif',         
		minusBottom			: 'minusbottom.gif',   
		nlPlus				: 'nolines_plus.gif',  
		nlMinus				: 'nolines_minus.gif'  
	};

	this.obj = objName;

	this.aNodes = [];

	this.aIndent = [];

	this.root = new Node(-1);

	this.selectedNode = null;

	this.selectedFound = false;

	this.completed = false;
	this.firstToString=true;
	
	//节点个数
    this.length = 0;
    //索引与CHECKBOX value的映射
	this.map = {};
};



// Adds a new node to the node array

myTree.prototype.add = function(id, pid, name, url, onclick, checked,title, target, icon, iconOpen, open) {

    this.length++;
	this.aNodes[this.aNodes.length] = new Node(id, pid, name, url, onclick, checked, title, target, icon, iconOpen, open);

};



// Open/close all nodes

myTree.prototype.openAll = function() {

	this.oAll(true);

};

myTree.prototype.closeAll = function() {

	this.oAll(false);

};



// Outputs the tree to the page

myTree.prototype.toString = function() {
	if(this.firstToString){

	this.icon.root				 =	this.icon.path + this.icon.root		;
	this.icon.folder		     =	this.icon.path + this.icon.folder	;	
	this.icon.folderOpen		 =	this.icon.path + this.icon.folderOpen;	
	this.icon.node				 =	this.icon.path + this.icon.node		;
	this.icon.empty				 =	this.icon.path + this.icon.empty		;
	this.icon.line				 =	this.icon.path + this.icon.line		;
	this.icon.join				 =	this.icon.path + this.icon.join		;
	this.icon.joinBottom		 =	this.icon.path + this.icon.joinBottom;	
	this.icon.plus				 =	this.icon.path + this.icon.plus		;
	this.icon.plusBottom		 =	this.icon.path + this.icon.plusBottom;	
	this.icon.minus				 =	this.icon.path + this.icon.minus		;
	this.icon.minusBottom		 =	this.icon.path + this.icon.minusBottom;
	this.icon.nlPlus			 =	this.icon.path + this.icon.nlPlus	;	
	this.icon.nlMinus			 =	this.icon.path + this.icon.nlMinus	;
	if(this.config.sort){
		this.aNodes.sort(nodeCompare);
	}
	this.firstToString = false;
	}
	var str = '<div class="myTree" id="'+this.obj+'_myTree">\n';

	if (document.getElementById) {

		if (this.config.useCookies) this.selectedNode = this.getSelected();

		str += this.addNode(this.root);

	} else str += 'Browser not supported.';

	str += '</div>';

	if (!this.selectedFound) this.selectedNode = null;

	this.completed = true;

	return str;

};


// Creates the tree structure

myTree.prototype.addNode = function(pNode) {

	var str = '';

	var n=0;

	if (this.config.inOrder) n = pNode._ai;

	for (n; n<this.aNodes.length; n++) {

		if (this.aNodes[n].pid == pNode.id) {

			var cn = this.aNodes[n];

			cn._p = pNode;

			cn._ai = n;

			this.setCS(cn);

			if (!cn.target && this.config.target) cn.target = this.config.target;

			if (cn._hc && !cn._io && this.config.useCookies) cn._io = this.isOpen(cn.id);

			if (!this.config.folderLinks && cn._hc) cn.url = null;
			/*if (cn.url==null || cn.url=="#" || cn.url=='')
			{
				cn.url = null;
			}*/
			if (this.config.useSelection && cn.id == this.selectedNode && !this.selectedFound) {

					cn._is = true;

					this.selectedNode = n;

					this.selectedFound = true;

			}

			str += this.node(cn, n);

			if (cn._ls) break;

		}

	}

	return str;

};



// Creates the node icon, url and text

myTree.prototype.node = function(node, nodeId) {

	var str = '<div class="' 

	if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id&&this.config.treeType=="menuTree")
		str +=this.config.pNodeClass+'">';
	else
		str +='myTreeNodeSel">';
	str += this.indent(node, nodeId);
	/*
	if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)
		str +='&nbsp;';
	*/
	if (this.config.useIcons) {

		if (!node.icon) node.icon = (this.root.id == node.pid) ?this.icon.root : ((node._hc) ?this.icon.folder :this.icon.node);

		if (!node.iconOpen) node.iconOpen = (node._hc) ?this.icon.folderOpen :this.icon.node;

		if (this.root.id == node.pid) {

			node.icon =this.icon.root;

			node.iconOpen =this.icon.root;

		}

		str += '<img id="i' + this.obj + nodeId + '" src="' + ((node._io) ? node.iconOpen : node.icon) + '" alt="" />';

	}

	if(this.config.treeType == "selectTree")
	{
	    this.map[node.id] = [nodeId,node.name,node.pid];
	    if(!(this.config.hideRoot && node.id==0))
		str +='<input type="checkbox" name="'+this.obj+'_ckname" class="checkbox" id="c' + this.obj + nodeId + '" value="'+(typeof(node.value)!='undefined'?node.value:node.id)+'" '+(node._checked?'checked':'')+' onclick="javascript:'+this.obj+'.sel('+nodeId+')" '+(this.config.disabled?'disabled=true':'')+'/>';
       
	}else if(this.config.treeType == "select-one"){
		if(nodeId>0){
			this.map[node.id] = [nodeId,node.name,node.pid];
			str +='<input type="radio" name="'+this.obj+'_ckname" class="checkbox" id="c' + this.obj + nodeId + '" value="'+node.id+'" '+(node._checked?'checked':'')+' onclick="javascript:'+this.obj+'.sel('+nodeId+')" '+(this.config.disabled?'disabled=true':'')+'/>';
		}
	}

	if (node.url) {

		str += '<a hideFocus="true" id="s' + this.obj + nodeId + '" class="' + ((this.config.useSelection) ? ((node._is ? 'nodeSel' : 'node')) : 'node') + '" href="' + node.url + '"';

		if (node.title) str += ' title="' + node.title + '"';

		if (node.target) str += ' target="' + node.target + '"';

		if (this.config.useStatusText) str += ' onmouseover="window.status=\'' + node.name + '\';return true;" onmouseout="window.status=\'\';return true;" ';

		if (this.config.useSelection && ((node._hc && this.config.folderLinks) || !node._hc))

			str += ' onclick="javascript: ' + this.obj + '.s(' + nodeId + ');'+node.onclick+';"';

		str += '>';

	}

	else if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)

		str += '<b><a hideFocus="true" id="s' + this.obj + nodeId + '" href="javascript: ' + this.obj + '.o(' + nodeId + ');'+node.onclick+';" class="node">';

	str += node.name;

	if (node.url || ((!this.config.folderLinks || !node.url) && node._hc)) str += '</a>';

	
	if ((!this.config.folderLinks || !node.url) && node._hc && node.pid != this.root.id)
		str +="</b>";
	/*
	if(this.config.treeType == "selectTree")
	{
		str +='<input type="checkbox" class="checkbox" id="ck' + this.obj + nodeId + '" value="'+node.id+'"/>'
	}*/
	str += '</div>';

	if (node._hc) {

		str += '<div id="d' + this.obj + nodeId + '" class="clip" style="display:' + ((this.root.id == node.pid || node._io) ? 'block' : 'none') + ';">';

		str += this.addNode(node);

		str += '</div>';

	}

	this.aIndent.pop();

	return str;

};



// Adds the empty and line icons

myTree.prototype.indent = function(node, nodeId) {

	var str = '';

	if (this.root.id != node.pid) {

		for (var n=0; n<this.aIndent.length; n++)

			str += '<img src="' + ( (this.aIndent[n] == 1 && this.config.useLines) ?this.icon.line :this.icon.empty ) + '" alt="" />';

		(node._ls) ? this.aIndent.push(0) : this.aIndent.push(1);

		if (node._hc) {

			str += '<a hideFocus="true" onclick="javascript: ' + this.obj + '.o(' + nodeId + ');"><img id="j' + this.obj + nodeId + '" src="';

			if (!this.config.useLines) str += (node._io) ?this.icon.nlMinus :this.icon.nlPlus;

			else str += ( (node._io) ? ((node._ls && this.config.useLines) ?this.icon.minusBottom :this.icon.minus) : ((node._ls && this.config.useLines) ?this.icon.plusBottom :this.icon.plus ) );

			str += '" alt="" /></a>';

		} else str += '<img src="' + ( (this.config.useLines) ? ((node._ls) ?this.icon.joinBottom :this.icon.join ) :this.icon.empty) + '" alt="" />';

	}

	return str;

};



// Checks if a node has any children and if it is the last sibling

myTree.prototype.setCS = function(node) {

	var lastId;

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n].pid == node.id) node._hc = true;

		if (this.aNodes[n].pid == node.pid) lastId = this.aNodes[n].id;

	}

	if (lastId==node.id) node._ls = true;

};



// Returns the selected node

myTree.prototype.getSelected = function() {

	var sn = this.getCookie('cs' + this.obj);

	return (sn) ? sn : null;

};



// Highlights the selected node

myTree.prototype.s = function(id) {

	if (!this.config.useSelection) return;

	var cn = this.aNodes[id];

	if (cn._hc && !this.config.folderLinks) return;

	if (this.selectedNode != id) {
    /*
		if (this.selectedNode || this.selectedNode==0) {

			eOld = document.getElementById("s" + this.obj + this.selectedNode);

			eOld.className = "node";

		}
		*/

		eNew = document.getElementById("s" + this.obj + id);

		eNew.className = "nodeSel";

		this.selectedNode = id;

		if (this.config.useCookies) this.setCookie('cs' + this.obj, cn.id);

	}

};

//select changed
myTree.prototype.sel = function(id){

	if(this.config.treeType=='menuTree'){return;}
	//var ck0 = document.getElementById("cd0");
	//if(ck0.checked) {alert("????????????????????????");ck0.checked=false;}
	selNod = document.getElementById('c' + this.obj + id);
	var cn = this.aNodes[id];
	cn._checked = selNod.checked;
	if(this.config.treeType=='selectTree'){
		this.selectAllChildren(cn,selNod.checked);
	}
	//this.checkParent(cn);
}



// Toggle Open or close

myTree.prototype.o = function(id) {

	var cn = this.aNodes[id];

	this.nodeStatus(!cn._io, id, cn._ls);

	cn._io = !cn._io;

	if (this.config.closeSameLevel) this.closeLevel(cn);

	if (this.config.useCookies) this.updateCookie();

};



// Open or close all nodes

myTree.prototype.oAll = function(status) {

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n]._hc && this.aNodes[n].pid != this.root.id) {

			this.nodeStatus(status, n, this.aNodes[n]._ls)

			this.aNodes[n]._io = status;

		}

	}

	if (this.config.useCookies) this.updateCookie();

};



// Opens the tree to a specific node

myTree.prototype.openTo = function(nId, bSelect, bFirst) {

	if (!bFirst) {

		for (var n=0; n<this.aNodes.length; n++) {

			if (this.aNodes[n].id == nId) {

				nId=n;

				break;

			}

		}

	}

	var cn=this.aNodes[nId];

	if (cn.pid==this.root.id || !cn._p) return;

	cn._io = true;

	cn._is = bSelect;

	if (this.completed && cn._hc) this.nodeStatus(true, cn._ai, cn._ls);

	if (this.completed && bSelect) this.s(cn._ai);

	else if (bSelect) this._sn=cn._ai;

	this.openTo(cn._p._ai, false, true);

};



// Closes all nodes on the same level as certain node

myTree.prototype.closeLevel = function(node) {

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n].pid == node.pid && this.aNodes[n].id != node.id && this.aNodes[n]._hc) {

			this.nodeStatus(false, n, this.aNodes[n]._ls);

			this.aNodes[n]._io = false;

			this.closeAllChildren(this.aNodes[n]);

		}

	}

}



// Closes all children of a node

myTree.prototype.closeAllChildren = function(node) {

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n].pid == node.id && this.aNodes[n]._hc) {

			if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);

			this.aNodes[n]._io = false;

			this.closeAllChildren(this.aNodes[n]);		

		}

	}

}
//select All children of a node

myTree.prototype.selectAllChildren = function(node,checked){
	for (var n=0; n<this.aNodes.length; n++) {
		if (this.aNodes[n].pid == node.id) {
			this.selectNode(checked,n);
			//if (this.aNodes[n]._io) this.nodeStatus(false, n, this.aNodes[n]._ls);
			//this.aNodes[n]._io = false;
			this.selectAllChildren(this.aNodes[n],checked);		
		}
	}
}

//change the node to selected or not

myTree.prototype.selectNode = function(selected,id){
	try{
	selNod = document.getElementById('c' + this.obj + id);
	selNod.checked = selected;
	}catch(ex){alert(ex.message);}
	this.aNodes[id]._checked = selected;
}

//check the node's parent to select or not

myTree.prototype.checkParent = function(node){
	//alert('test');

	var pNode =null;
	var allChecked = true;
	var i = 0;
	for (var n=0; n<this.aNodes.length; n++) {
		if( this.aNodes[n].id == node.pid ){
			pNode = this.aNodes[n];
			i = n;
			break;
		}
	}
	//   ?????????????? if all child select ,select parent
	if(pNode){
			for(var n=0; n<this.aNodes.length; n++){			
				if( (this.aNodes[n].pid == pNode.id) &&(!this.aNodes[n]._checked)){
					this.selectNode(false,i);
					allChecked = false;
					break;
				}
			}
		if(allChecked){
			this.selectNode(true,i);
		}
		this.checkParent(pNode);
	}
    

}
// Change the status of a node(open or closed)

myTree.prototype.nodeStatus = function(status, id, bottom) {

	eDiv	= document.getElementById('d' + this.obj + id);

	eJoin	= document.getElementById('j' + this.obj + id);

	if (this.config.useIcons) {

		eIcon	= document.getElementById('i' + this.obj + id);

		eIcon.src = (status) ? this.aNodes[id].iconOpen : this.aNodes[id].icon;

	}

	eJoin.src = (this.config.useLines)?

	((status)?((bottom)?this.icon.minusBottom:this.icon.minus):((bottom)?this.icon.plusBottom:this.icon.plus)):

	((status)?this.icon.nlMinus:this.icon.nlPlus);

	eDiv.style.display = (status) ? 'block': 'none';

};





// [Cookie] Clears a cookie

myTree.prototype.clearCookie = function() {

	var now = new Date();

	var yesterday = new Date(now.getTime() - 1000 * 60 * 60 * 24);

	this.setCookie('co'+this.obj, 'cookieValue', yesterday);

	this.setCookie('cs'+this.obj, 'cookieValue', yesterday);

};



// [Cookie] Sets value in a cookie

myTree.prototype.setCookie = function(cookieName, cookieValue, expires, path, domain, secure) {

	document.cookie =

		escape(cookieName) + '=' + escape(cookieValue)

		+ (expires ? '; expires=' + expires.toGMTString() : '')

		+ (path ? '; path=' + path : '')

		+ (domain ? '; domain=' + domain : '')

		+ (secure ? '; secure' : '');

};



// [Cookie] Gets a value from a cookie

myTree.prototype.getCookie = function(cookieName) {

	var cookieValue = '';

	var posName = document.cookie.indexOf(escape(cookieName) + '=');

	if (posName != -1) {

		var posValue = posName + (escape(cookieName) + '=').length;

		var endPos = document.cookie.indexOf(';', posValue);

		if (endPos != -1) cookieValue = unescape(document.cookie.substring(posValue, endPos));

		else cookieValue = unescape(document.cookie.substring(posValue));

	}

	return (cookieValue);

};



// [Cookie] Returns ids of open nodes as a string

myTree.prototype.updateCookie = function() {

	var str = '';

	for (var n=0; n<this.aNodes.length; n++) {

		if (this.aNodes[n]._io && this.aNodes[n].pid != this.root.id) {

			if (str) str += '.';

			str += this.aNodes[n].id;

		}

	}

	this.setCookie('co' + this.obj, str);

};



// [Cookie] Checks if a node id is in a cookie

myTree.prototype.isOpen = function(id) {

	var aOpen = this.getCookie('co' + this.obj).split('.');

	for (var n=0; n<aOpen.length; n++)

		if (aOpen[n] == id) return true;

	return false;

};

myTree.prototype.getSelectedNodes = function(token){
	var selectedNodes = '';
	for (var n=0; n<this.aNodes.length; n++) {
		if(this.aNodes[n]._checked){
		    if(this.aNodes[n].pid =='-1') continue;
			selectedNodes += this.aNodes[n].id + token;
		}
	}
	return selectedNodes.substring(0,selectedNodes.length-1);
};

myTree.prototype.reload = function(){
    var str = '';
    if (document.getElementById) {

		if (this.config.useCookies) this.selectedNode = this.getSelected();

		str += this.addNode(this.root);

	} else str += 'Browser not supported.';
    if (!this.selectedFound) this.selectedNode = null;
	this.completed = true;
	document.getElementById(this.obj+'_myTree').innerHTML = str;
};

myTree.prototype.clear = function(){
	this.aNodes.clear();
	this.aIndent.clear();
	this.map = {};
};

myTree.prototype.reset = function(){
   this.clear();
   document.getElementById(this.obj+'_myTree').innerHTML = '';
}

// If Push and pop is not implemented by the browser

if (!Array.prototype.push) {

	Array.prototype.push = function array_push() {

		for(var i=0;i<arguments.length;i++)

			this[this.length]=arguments[i];

		return this.length;

	}

};

if (!Array.prototype.pop) {

	Array.prototype.pop = function array_pop() {

		lastElement = this[this.length-1];

		this.length = Math.max(this.length-1,0);

		return lastElement;

	}

};

Array.prototype.clear = function() {
	if (this.length > 0) {
		this.splice(0, this.length);
	}
}