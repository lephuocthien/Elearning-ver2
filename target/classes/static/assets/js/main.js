$('document').ready(function(){
        $('.del-btn').on('click', function(event){
            event.preventDefault();
            var href = $(this).attr('href');
            $('#delModal #delRef').attr('href', href);
            $('#delModal').modal('show');
        });

        $("#coursePrice").keyup(function(){
            var coursePrice = $("#coursePrice").val();
            var courseDiscount = $("#courseDiscount").val();
            if (coursePrice >= 0){
                if (courseDiscount > 0){
                    var promotionPrice = coursePrice * (100-courseDiscount) / 100;
                    $("#promotionPriceShow").val(promotionPrice);
                    $("#promotionPrice").val(promotionPrice);
                } else {
                    $("#promotionPriceShow").val(coursePrice);
                    $("#promotionPrice").val(promotionPrice);
                }
            }
        });

        $("#courseDiscount").keyup(function(){
            var coursePrice = $("#coursePrice").val();
            var courseDiscount = $("#courseDiscount").val();
            if (coursePrice >= 0){
                if (courseDiscount > 0){
                    var promotionPrice = coursePrice * (100-courseDiscount) / 100;
                    $("#promotionPriceShow").val(promotionPrice);
                    $("#promotionPrice").val(promotionPrice);
                } else {
                    $("#promotionPriceShow").val(coursePrice);
                    $("#promotionPrice").val(promotionPrice);
                }
            }
        });

        $("#iconClass").change(function(){
            $("#iconPreview").addClass($("#iconClass").val());
        });
    })