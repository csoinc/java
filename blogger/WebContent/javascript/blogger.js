/**
 * blogger functions
 * @author Owen Ou
 * @version $Id$
 */
var blogger = new function() {
	var gid; 
    var mid;

    return {
		MODULE_MESSAGE_LIST: "message_list",
		KEY_MESSAGE_LIST: "MessageList",
        		
		blogMessageForm: "blogMessageForm",
		initBlogger: function(groupId, messageId) {
			gid = groupId;
			mid = messageId;
		},
		/**
		 * create the page links based on the blog request and response  
		 * @author Owen Ou 
		 * @version 2.0
		 */
		createPageLinksElements: function(elemId,childId,totalPages,currPage,linkLength,idPrefix,actionName) {
			var length = linkLength; 
			var divElem = document.getElementById(elemId);
			var childElem = document.getElementById(childId);
			divElem.removeChild(childElem); 
			var spanElem = document.createElement('span');
			spanElem.setAttribute('id', childId);
			spanElem.setAttribute('class', "");

			var ceil = Math.ceil(currPage/length);
			var floor = Math.floor(currPage/length);
		    if (ceil == floor) floor--;
		    
		    var minPage = length * floor + 1;
		    var maxPage = length * ceil;
		
		    if (maxPage > totalPages) maxPage = totalPages;
		
		    //alert("created <<");
			if (minPage >= length && minPage <= totalPages ) {
				var link = document.createElement("a");
				link.setAttribute("href", "./" + actionName + "?gid=" + gid + "&page=1");
				var span = document.createElement("span");
				link.setAttribute("class", "");
				var val = document.createTextNode("||");
				var sp = document.createTextNode(" ");
				span.appendChild(val);
				link.style.fontSize = "10pt";	
				link.appendChild(span);
				spanElem.appendChild(link);
				spanElem.appendChild(sp);

				link = document.createElement("a");
				link.setAttribute("href", "./" + actionName + "?gid=" + gid + "&page=" + (minPage-1));
				span = document.createElement("span");
				link.setAttribute("class", "");
				val = document.createTextNode("<<");
				sp = document.createTextNode(" ");
				span.appendChild(val);
				link.style.fontSize = "10pt";	
				link.appendChild(span);
				spanElem.appendChild(link);
				spanElem.appendChild(sp);
			}	    

		    //alert("created min - max");
		    for (i = minPage; i <= maxPage; i++) {
				var link = document.createElement("a");
				link.setAttribute("href", "./" + actionName + "?gid=" + gid + "&page=" + i);
				var span = document.createElement("span");
				link.setAttribute("id", "blog_"+idPrefix+"_"+i);
				link.setAttribute("class", "");
				var val = document.createTextNode("["+i+"]");
				var sp = document.createTextNode(" ");
				span.appendChild(val);
				if (i == currPage) {
					link.setAttribute("title", "Current Page " + i);
				 	link.style.color = "red";
				} else {
					link.setAttribute("title", "Page " + i);
				}	
				link.style.fontSize = "10pt";	
				link.appendChild(span);
				spanElem.appendChild(link);
				spanElem.appendChild(sp);
			}

			//create >> element here
			if (maxPage < totalPages) {
				var link = document.createElement("a");
				link.setAttribute("href", "./" + actionName + "?gid=" + gid + "&page=" + (maxPage+1));
				var span = document.createElement("span");
				link.setAttribute("class", "");
				var val = document.createTextNode(">>");
				var sp = document.createTextNode(" ");
				span.appendChild(val);
				link.style.fontSize = "10pt";	
				link.appendChild(span);
				spanElem.appendChild(link);
				spanElem.appendChild(sp);

				link = document.createElement("a");
				link.setAttribute("href", "./" + actionName + "?gid=" + gid + "&page=" + totalPages);
				span = document.createElement("span");
				link.setAttribute("class", "");
				val = document.createTextNode("||");
				sp = document.createTextNode(" ");
				span.appendChild(val);
				link.style.fontSize = "10pt";	
				link.appendChild(span);
				spanElem.appendChild(link);
				spanElem.appendChild(sp);
			}	

			divElem.appendChild(spanElem);
		}
    };
};
