

// GỌI API LOAD THÔNG TIN ROLE LÊN FORM
// window.location.href = `/search.html`;
let login = function () {
    let email = document.getElementById("loginEmail").value;
    let password = document.getElementById("loginPassword").value;
    axios({
        url: `http://localhost:8087/api/auth`,
        method: "POST",
        data: {
            email: email,
            password: password
        }
    })
        .then(function (resp) {
            let token = resp.data
            console.log(resp.data);
            // loadUserInfor();
            axios({
                url: `http://localhost:8087/api/user/get-user-by-token`,
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                }
            })
                .then(function (resp) {
                    let user = resp.data;
                    console.log(resp.data);
                    if (user.roleId !== 3) {
                        document.getElementById("loginMess").innerHTML = "Invalid email or password incorrect !";
                        $(function () {
                            $('#myModalLogin').modal({
                                show: true
                            });
                        });
                    } else {
                        alert("Đăng nhập thành công!");
                        //Lưu token vào localstorage
                        localStorage.setItem("USER_TOKEN", token);
                        localStorage.setItem("USER_INFOR", JSON.stringify(user));
                        setInforDropDown();
                    }

                })
                .catch(function (e) {
                    console.log(e.resp)
                });

        })
        .catch(function (err) {
            console.log(err.response);
            document.getElementById("loginMess").innerHTML = "Invalid email or password incorrect !";
            $(function () {
                $('#myModalLogin').modal({
                    show: true
                });
            });
        });
}

let logout = function () {
    localStorage.removeItem('USER_TOKEN');
    localStorage.removeItem('USER_INFOR');
    window.location.href = "index.html";
}

let register = function () {
    let email = document.getElementById("regEmail").value;
    let password = document.getElementById("regPassword").value;
    let confirmPassword = document.getElementById("regConfirmPassword").value;
    // let roleId = document.getElementById("regRoleId").value;
    let fullname = document.getElementById("regFullname").value;
    //  console.log(email);
    //  console.log(password);
    //  console.log(confirmPassword);
    //  console.log(roleId);
    if ((password !== confirmPassword) || (!password)) {
        document.getElementById("regMess").innerHTML = "Password incorrect !";
        $(function () {
            $('#myModalRegister').modal({
                show: true
            });
        });
    }
    else {
        axios({
            url: `http://localhost:8087/api/auth/register`,
            method: "POST",
            data: {
                fullname: fullname,
                email: email,
                password: password,
                roleId: 3
            }
        })
            //Xữ lý mã trạng thái bắt đầu bằng số 2
            .then(function (response) {
                console.log(response.data);
                alert(`Đăng ký thành công! Đăng nhập lại vào hệ thống!`);
            })
            //Xữ lý mã trạng thái còn lại
            .catch(function (e) {
                document.getElementById("regMess").innerHTML = "Invalid email !";
                $(function () {
                    $('#myModalRegister').modal({
                        show: true
                    });
                });
            });
    }

}

let loadUserInfor = function () {
    //console.log(token);
    if (token != null){
        if (token != ""){
        axios({
            url: `http://localhost:8087/api/user/get-user-by-token`,
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            .then(function (resp) {
                let userTemp = resp.data;
                console.log(resp.data);
                localStorage.setItem("USER_INFOR", JSON.stringify(userTemp));
                setInforDropDown();
                setProfile();
            })
            .catch(function (e) {
                console.log(e.resp)
            });
        }
    }
};
