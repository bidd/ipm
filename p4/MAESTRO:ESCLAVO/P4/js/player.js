var PushPlayer = (function () {

	var player = null;

	function new_player() {
		console.log("onYouTubeIframeAPIReady");
		player = new YT.Player('yt-player', {
    		height: '390',
			width: '640',
			videoId: 'YD9SEtaVKgA',
			events: {
				'onReady': onPlayerReady
			}
 	 	});
	}

	function onPlayerReady(event) {
		console.log("onPlayerReady");
	}

    function play(){
        player.playVideo();    
    }

    function pause(){
        player.pauseVideo();
    }

    function stop(){
        player.stopVideo();
    }
	
    function video_by_id(id){
        player.cueVideoById(id);
    }

    function video_by_url(url){
        player.cueVideoByUrl(url);
    }

	return {
		new_player: new_player,
        play: play,
        pause: pause,
        stop: stop,
        video_by_id: video_by_id,
        video_by_url: video_by_url
	}
	
})();



window.onYouTubeIframeAPIReady = PushPlayer.new_player;


