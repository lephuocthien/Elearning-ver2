var user = JSON.parse(localStorage.getItem('USER_INFOR'));
let token = localStorage.getItem("USER_TOKEN");
console.log(user);
if (!user) {
    //Nếu token null hoặc rỗng (chưa đăng nhập)
    window.location.href = "index.html";
}
let imgName="";
let uploadFile = document.getElementById("uploadFile");
uploadFile.addEventListener("change", function (event) {
    let file = event.target.files[0];
    console.log(file);
    let formData = new FormData();
    formData.append("file", file);

    axios({
        url: `http://localhost:8087/api/user/file/upload/${user.id}`,
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token
        },
        data: formData
    })
        .then(function (resp) {
            imgName = resp.data;
            let imgUrl = `http://localhost:8087/api/user/file/load/${user.id}/${imgName}`;
            document.getElementById('imgAvatar').setAttribute("src", imgUrl);
        })
        .catch(function (e) {
            console.log(e.resp)
        });
})
let setProfile = function () {
    user = JSON.parse(localStorage.getItem('USER_INFOR'));
    console.log(user)
    document.getElementById("bannerProFullname").innerHTML = user.fullname;
    document.getElementById("bannerProEmail").innerHTML = user.email;

    document.getElementById("fmFullname").value = user.fullname;
    document.getElementById("fmEmail").value = user.email;
    document.getElementById("fmAddress").value = user.address;
    document.getElementById("fmPhone").value = user.phone;

    document.getElementById("securityEmail").value = user.email;
    if (!(!user.avatar)) {
        let imgUrl = `http://localhost:8087/api/user/file/load/${user.id}/${user.avatar}`;
        document.getElementById('imgAvatar').setAttribute("src", imgUrl);
    }

};

let updateProfile = function () {
    let fullname = document.getElementById("fmFullname").value;
    let address = document.getElementById("fmAddress").value;
    let phone = document.getElementById("fmPhone").value;
    let email = document.getElementById("fmEmail").value;
    if(!(!user.avatar)){
        imgName=user.avatar;
    }
    axios({
        url: `http://localhost:8087/api/user/update/${user.id}`,
        method: "PUT",
        responseType: 'json',
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
let updatePassword = function () {
    let password = document.getElementById("securityPassword").value;
    let confirmPassword = document.getElementById("securityConfirmPassword").value;
    if(!(!user.avatar)){
        imgName=user.avatar;
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
        axios({
            url: `http://localhost:8087/api/user/update/${user.id}`,
            method: "PUT",
            responseType: 'json',
            headers: {
                "Authorization": "Bearer " + token
            },
            data: {
                avatar:  imgName,
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
                document.getElementById("updateSecurityMess").className="text-success";
                document.getElementById("updateSecurityMess").innerHTML = "Update success !";

            })
            //Xữ lý mã trạng thái còn lại
            .catch(function (e) {
                console.log(e.response);
                document.getElementById("updateSecurityMess").className="text-danger";
                document.getElementById("updateSecurityMess").innerHTML = "Password incorrect !";
            });
    }
};

let updateAvatar = function () {
    if (!(!imgName)) {
        axios({
            url: `http://localhost:8087/api/user/update/${user.id}`,
            method: "PUT",
            responseType: 'json',
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
                password: ""
            }
        })
            //Xữ lý mã trạng thái bắt đầu bằng số 2
            .then(function (response) {
                console.log(response.data);
                loadUserInfor();
                // setProfile();
                document.getElementById("updateAvatarMess").className="text-success";
                document.getElementById("updateAvatarMess").innerHTML = "Update success !";
            })
            //Xữ lý mã trạng thái còn lại
            .catch(function (e) {
                console.log(e.response);
                document.getElementById("updateAvatarMess").className="text-danger";
                document.getElementById("updateAvatarMess").innerHTML = "Invalid Image !";
            });
    }
};

setProfile();