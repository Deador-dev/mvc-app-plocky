$(function() {
	$(".selectlink-control").click(function(){
		let popup = $(".menu_popup");
		popup.slideToggle(200, function(){
			$('.selectlink ul').not(popup).slideUp(200);
			if (popup.is(':hidden')) {
				$('body').removeClass('body_pointer');
			} else {
				$('body').addClass('body_pointer');
			}
		});
		return false;
	});

	$(document).on('click', function(e){
		if (!$(e.target).closest('.selectlink').length){
			$('body').removeClass('body_pointer');
			$('.selectlink ul').slideUp(200);
		}
	});
});