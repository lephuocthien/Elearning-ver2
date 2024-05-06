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
    });
let uploadFile = document.getElementById("uploadFile");
uploadFile.addEventListener("change", function (event) {
    if (event.target.files) {
        var filesAmount = event.target.files.length;
        for (i = 0; i < filesAmount; i++) {
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('imgItem').setAttribute("src", e.target.result);
                console.log(e.target.result);
            }
            reader.readAsDataURL(event.target.files[i]);
        }
    }
});
