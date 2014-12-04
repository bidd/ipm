var PushClient = (function() {

	var ws_server_uri = "ws://localhost:8888/ws";
	
	var dom = {
		logging: document.querySelector("#log")
	};
	
	function init() {
		bindWSActions();
	}
	
	function bindWSActions() {
		var ws = new WebSocket(ws_server_uri);
		
        ws.onopen = function(e) { 
        	append_log("Websocket opened");
        }
        
        ws.onclose = function(e) { 
        	append_log("Websocket closed");
        }
        
        ws.onmessage = function(e) {         	
        	append_log(e.data);
            if (e.data == "play") {
                PushPlayer.play();     
            } else if (e.data == "pause") {
                PushPlayer.pause();
            } else if (e.data == "stop") {
                PushPlayer.stop();
            } else if (e.data.substring(0,1) == "i") {
                PushPlayer.video_by_id(e.data.substring(2, e.data.length));
            } else if (e.data.substring(0,1) == "u") {
                PushPlayer.video_by_url(e.data.substring(2, e.data.length));
            }
		}
	
	}
		
	
	function append_log(message) {
		var p = document.createElement("p");
		p.innerHTML= message;
		dom.logging.appendChild(p);			        	
    }
    
    return {
    	init: init
    };
      
})();


PushClient.init();
