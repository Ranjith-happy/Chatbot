<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<div id="chatApp">


<div class="chatBox" id="chatBox" hidden>
  <div class="card">
 
 <header class="card-header header-title">
    <p class="card-header-title">
     <!--  <i class="fa fa-circle is-online"></i> -->Chat Live with others 
    </p>
    <a class="card-header-icon" id="chat-close" title="close chatbot">
      <span class="icon">
        <i class="fa fa-close"></i>
      </span>
    </a>
  </header>
 <div id="chatbox-area" data-toggle="1">
  <div class="card-content chat-content" id="chatbox-content">
    <div class="content">
      <div class="chat-message-group">
        <div class="chat-thumb">
          <figure class="image is-32x32">
            
          </figure>
        </div>
       </div>
      <div class="chat-message-group">
        <%-- <div class="chat-messages">
          <div class="message">Hi There!!!</div>
          <div class="from">Today <fmt:formatDate type="time" dateStyle="short" timeStyle="short" value="${now}" /></div>
        </div>
 --%> 
 <div class="textContent">
      <textarea id="chatTextarea" class="chat-textarea" placeholder="Type your question here.."></textarea>
      
      <div class="send-icon" >
    <a class="button-chatbot is-white" title="send message"><i class="fa fa-paper-plane" aria-hidden="true"></i></a></div>
    
    </div>
 </div>
      <div class="chat-message-group typing-text" hidden>
        <div class="typing">Typing</div>
        <div class="spinner">
              <div class="bounce1"></div>
              <div class="bounce2"></div>
              <div class="bounce3"></div>
         </div>
      </div>
    </div>
  </div>
  <div class="chat-message-group typing-text-display" hidden>
        <div class="typing">typing</div>
        <div class="spinner">
              <div class="bounce1"></div>
              <div class="bounce2"></div>
              <div class="bounce3"></div>
         </div>
      </div>
  <footer class="card-footer" id="chatBox-textbox">
    <!-- <div class="textContent">
      <textarea id="chatTextarea" class="chat-textarea" placeholder="Type message here..."></textarea>
      
      <div id="start-record-btn" class="fa fa-microphone" style="font-size:24px; color:#0f7384; top:-5px"></div>
      
      
      <div class="send-icon" >
      
    <a class="button-chatbot is-white" title="send message"><i class="fa fa-paper-plane" aria-hidden="true"></i></a></div>
    
    </div>
     -->
  </footer>
  </div>
</div>
</div>

<div class="chat-bot-icon" title="livechat" id="chat-icon">
 <i class="fa fa-comments" aria-hidden="true"></i>
</div>
 
<div class="emojiBox" style="display: none">
  <div class="box">
  
  </div>
</div>

</div> 


