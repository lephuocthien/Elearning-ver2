var user;
let setInforDropDown = function () {
    user = JSON.parse(localStorage.getItem('USER_INFOR'));
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
setInforDropDown();