<%-- 
    Document   : SearchResult
    Created on : Mar 1, 2016, 10:43:04 PM
    Author     : sagarpatel
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="shtml" tagdir="/WEB-INF/tags/"%>
<%@page import="xyz.brnbn.wikisearch.index.retrieve.*,java.util.ArrayList" %>
<shtml:ResultPageTop />

    <div class="container-fluid">

        <div class="row">
        <div class="col-xs-12 col-sm-12 col-md-6 col-md-offset-1 col-lg-5 col-lg-offset-1">
        <div class="search-results">                
       
       	<%  String query = request.getParameter("query"); 
       		ArrayList<Document> al = IndexRetrieval.retrieve(query); 
       		//query = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, query);
       		%>
		       
       
        <h5>Search Results for “<%=query%>”</h5>
        
            
            <table>
            
            <%for (Document t : al) { 
            	//t = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, t); %>
	           
	           <tr>
	           <td style="width:70%">
	            <div class="link">
	                <a href="https://en.wikipedia.org/w/index.php?title=<%=t.getTitle()%>" s="false">
	                    <div class="link-title"><%=t.getTitle()%></div>
	                    <div class="link-host">https://en.wikipedia.org/w/index.php?title=<%=t%></div>
	                </a>
	            </div>
	           </td>          
	           
	           </tr>
            
            <% } %>
           
           </table>
         
        
        
        </div></div></div>

    </div>