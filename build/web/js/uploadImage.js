function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#uploadImg')
                    .attr('src', e.target.result)
                    .width(280)
                    .height(180);
        };

        reader.readAsDataURL(input.files[0]);
    }
}