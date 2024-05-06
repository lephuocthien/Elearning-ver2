//loadUserInfor();
var user = JSON.parse(localStorage.getItem('USER_INFOR'));
let token = localStorage.getItem("USER_TOKEN");
if (!user) {
    //Nếu token null hoặc rỗng (chưa đăng nhập)
    window.location.href = "index.html";
}
let imgName=null;
let uploadFile = document.getElementById("uploadFile");
uploadFile.addEventListener("change", function (event) {
    if (event.target.files) {
        var filesAmount = event.target.files.length;
        for (i = 0; i < filesAmount; i++) {
            var reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('imgAvatar').setAttribute("src", e.target.result);
                console.log(e.target.result);
            }
            reader.readAsDataURL(event.target.files[i]);
        }
    }
});

let setProfile = function () {
    user = JSON.parse(localStorage.getItem('USER_INFOR'));
    document.getElementById("bannerProFullname").innerHTML = user.fullname;
    document.getElementById("bannerProEmail").innerHTML = user.email;
    document.getElementById("fmFullname").value = user.fullname;
    document.getElementById("fmEmail").value = user.email;
    document.getElementById("fmAddress").value = user.address;
    document.getElementById("fmPhone").value = user.phone;
    document.getElementById("securityEmail").value = user.email;
    if (!(!user.avatar)) {
        //let imgUrl = Url + `api/user/file/load/${user.id}/${user.avatar}`;
        document.getElementById('imgAvatar').setAttribute("src", "data:image/png;base64," + user.avatar);
    }

};

let updateProfile = async function () {
    let fullname = document.getElementById("fmFullname").value;
    let address = document.getElementById("fmAddress").value;
    let phone = document.getElementById("fmPhone").value;
    let email = document.getElementById("fmEmail").value;
    //let imgName = null;
    if (!(!user.avatar)) {
        imgName = user.avatar;
    }
    await axios({
        url: Url + `api/user/update/${user.id}`,
        method: "PUT",
        responseType: 'json',
        timeout: 30000, // Set a timeout of 30 seconds
        headers: {
            "Authorization": "Bearer " + token
        },
        data: {
            avatar: imgName,
            id: user.id,
            fullname: fullname,
            email: email,
            address: address,
            phone: phone,
            roleId: user.roleId,
            password: ""
        }
    })
        //Xữ lý mã trạng thái bắt đầu bằng số 2
        .then(function (response) {
            console.log(response.data);
            if (email === user.email) {
                loadUserInfor();
                // setProfile();
                document.getElementById("updateMess").className = "text-success";
                document.getElementById("updateMess").innerHTML = "Update success !";
            } else {
                alert(`Cập nhật thành công email! Vui lòng đăng nhập lại !`);
                logout();
            }
        })
        //Xữ lý mã trạng thái còn lại
        .catch(function (e) {
            console.log(e.response);
            document.getElementById("updateMess").className = "text-danger";
            document.getElementById("updateMess").innerHTML = "Invalid email !";
        });
};

let updatePassword = async function () {
    let password = document.getElementById("securityPassword").value;
    let confirmPassword = document.getElementById("securityConfirmPassword").value;
    if (!(!user.avatar)) {
        imgName = user.avatar;
    }
    if ((password !== confirmPassword) || (!password)) {
        document.getElementById("updateSecurityMess").classList.add("text-danger");
        document.getElementById("updateSecurityMess").innerHTML = "Password incorrect !";
        $(function () {
            $('#myModalRegister').modal({
                show: true
            });
        });
    } else {
        await axios({
            url: Url + `api/user/update/${user.id}`,
            method: "PUT",
            responseType: 'json',
            timeout: 30000, // Set a timeout of 30 seconds
            headers: {
                "Authorization": "Bearer " + token
            },
            data: {
                avatar: imgName,
                id: user.id,
                fullname: user.fullname,
                email: user.email,
                address: user.address,
                phone: user.phone,
                roleId: user.roleId,
                password: password
            }
        })
            //Xữ lý mã trạng thái bắt đầu bằng số 2
            .then(function (response) {
                console.log(response.data);
                loadUserInfor();
                document.getElementById("updateSecurityMess").className = "text-success";
                document.getElementById("updateSecurityMess").innerHTML = "Update success !";

            })
            //Xữ lý mã trạng thái còn lại
            .catch(function (e) {
                console.log(e.response);
                document.getElementById("updateSecurityMess").className = "text-danger";
                document.getElementById("updateSecurityMess").innerHTML = "Password incorrect !";
            });
    }
};

let updateAvatar = async function () {
    debugger
    let formData = new FormData();
    let file = document.getElementById("uploadFile").files[0];
    formData.append("file", file);
    await axios({
        url: Url + `api/user/file/upload/${user.id}`,
        method: "POST",
        responseType: 'json',
        timeout: 30000, // Set a timeout of 30 seconds
        headers: {
            "Authorization": "Bearer " + token
        },
        data: formData
    })
        .then(function (response) {
            console.log(response.data);
            loadUserInfor();
            // setProfile();
            document.getElementById("updateAvatarMess").className = "text-success";
            document.getElementById("updateAvatarMess").innerHTML = "Update success !";
        })
        //Xữ lý mã trạng thái còn lại
        .catch(function (e) {
            debugger
            console.log(e.response);
            document.getElementById("updateAvatarMess").className = "text-danger";
            document.getElementById("updateAvatarMess").innerHTML = "Invalid Image !";
        });
};
setProfile();