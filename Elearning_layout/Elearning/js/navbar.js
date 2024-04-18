
let getAllCategory = function(){
    axios({
        url: "http://localhost:8087/api/category",
        method: "GET"
    })
        //Xữ lý mã trạng thái bắt đầu bằng số 2
        .then(function (response) {
            //Truy xuất đến thẻ body( nơi sẽ chứa giao diện)
            let tbody = document.getElementById("categoryMenuDropdown");
            //Thay đổi nội dung thẻ tbody
            let content = '';
            for (let item of response.data) {
                content += `
           <a class="dropdown-item" href="#">
            <i class="${item.icon} mr-1"></i>
            <span>${item.title}</span>
            </a>
           `;
            }

            tbody.innerHTML = content;
        })
        //Xữ lý mã trạng thái còn lại
        .catch(function (e) {
            console.log(e.response)
        });
    axios({
        url: "http://localhost:8087/api/category",
        method: "GET"
    })
        //Xữ lý mã trạng thái bắt đầu bằng số 2
        .then(function (response) {
            //Truy xuất đến thẻ body( nơi sẽ chứa giao diện)
            let tbody = document.getElementById("categoryMenuRow");
            //Thay đổi nội dung thẻ tbody
            let content = '';
            for (let item of response.data) {
                content += `
                <div class="col-md-3">
                <a class="category">
                    <i class="${item.icon}"></i>
                    <span>${item.title}</span>
                </a>
            </div>
               `;
            }

            tbody.innerHTML = content;
        })
        //Xữ lý mã trạng thái còn lại
        .catch(function (e) {
            console.log(e.response)
        });
}
let search = function () {
    //event.preventDefault();
    let key = document.getElementById("key").value;
    //debugger
    window.location.href = "result.html?key="+key;
    //window.location.href = `result.html?key=${key}`;
}
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
                        location.reload();
                        //setInforDropDown();
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
let setInforDropDown = function () {
    let user = JSON.parse(localStorage.getItem('USER_INFOR'));
    let tbody = document.getElementById("userInforDropdown");
    let imgUrl = `https://i.udemycdn.com/user/200_H/anonymous_3.png`;
    let content = `
    <button class="btn btn-outline-secondary" data-toggle="modal" data-target="#myModalLogin">Login</button>
    <button class="btn btn-danger ml-2" data-toggle="modal" data-target="#myModalRegister">Sign up</button>
    `
    if (!(!user)) {
        if (!(!user.avatar))
            imgUrl = `http://localhost:8087/api/user/file/load/${user.id}/${user.avatar}`;
        content = '';
        content += `
    <div class="collapse navbar-collapse" id="collapsibleNavId">
        <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item dropdown"><a class="nav-link dropdown-toggle" href="#" id="dropdownId"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <img id="imgDropdown" src="${imgUrl}" alt="Avatar" width="36" class="avatar" />
                Hi, ${user.fullname}
                                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownId">
                    <a class="dropdown-item" href="profile.html">Profile</a>
                    <a class="dropdown-item" href="course.html">My Course</a>
                    <a class="dropdown-item" onclick = "logout()">Logout</a>
                </div>
            </li>
        </ul>
    </div>
    `;
    }
    tbody.innerHTML = content;
}
getAllCategory();
setInforDropDown();