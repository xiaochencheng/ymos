package com.ymos.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

public  class Query implements Serializable{

    public Query() {
    }

    private LinkedHashMap<String, QueryStr> qstrs=new LinkedHashMap<>();
	
	@Override
	public String toString() {
		return "Query [qstrs=" + qstrs + "]";
	}
	public Collection<QueryStr> getQstrs() {
		return this.qstrs.values();
	}
    public void addQstrs(String name,Object value){
    	this.qstrs.put(name, new QueryStr(name,value));
    }
    public String getQueryStr(){
    	if (this.qstrs!=null&&!this.qstrs.isEmpty()) {
			StringBuilder qst=new StringBuilder();
			Iterator<QueryStr> it=this.qstrs.values().iterator();
			while (it.hasNext()) {
				QueryStr qs=it.next();
				qst.append(qs.name).append("=").append(qs.value).append("&");
			}
			return qst.deleteCharAt(qst.length()-1).toString();
		}else{
			return "";
		}
    }
    public static class NameQuery extends Query {
        protected String name;
        public NameQuery() {
        }
        public String getName() {
            return this.name != null && this.name.trim().length() >= 1?"%" + this.name + "%":null;
        }
        public void setName(String name) {
            this.name = name;
            this.addQstrs("name", name); 
        }
    }
	public static class QueryStr implements Serializable{
		private String name;
        private Object value;

        public QueryStr() {
        }

        public QueryStr(String name, Object value) {
            this.name = name;
            this.value = value;
        }
        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public Object getValue() {
            return this.value;
        }
        public void setValue(String value) {
            this.value = value;
        }
		@Override
		public String toString() {
			return "QueryStr [name=" + name + ", value=" + value + "]";
		}
    }
}
