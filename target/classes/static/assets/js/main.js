$('document').ready(function(){
        $('.del-btn').on('click', function(event){
            event.preventDefault();
            var href = $(this).attr('href');
            $('#delModal #delRef').attr('href', href);
            $('#delModal').modal();
        });
    })