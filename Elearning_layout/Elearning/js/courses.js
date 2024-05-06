//loadUserInfor();
var user = JSON.parse(localStorage.getItem('USER_INFOR'));
let token = localStorage.getItem("USER_TOKEN");
if (!user) {
    //Nếu token null hoặc rỗng (chưa đăng nhập)
    window.location.href = "index.html";
}
document.getElementById("bannerCourseFullname").innerHTML = user.fullname + "'s Course";
document.getElementById("bannerCourseEmail").innerHTML = user.email;

let setCourse = function () {
    user = JSON.parse(localStorage.getItem('USER_INFOR'));
    // if (!(!user.avatar)) {
    //     let imgUrl = Url +  `api/user/file/load/${user.id}/${user.avatar}`;
    //     document.getElementById('imgAvatar').setAttribute("src", imgUrl);
    // }
    let tbody = document.getElementById("listMyCourse");
    //Thay đổi nội dung thẻ tbody
    //let imgUrl = `https://i.udemycdn.com/user/200_H/anonymous_3.png`;
    let content = '';
    for (let item of user.courses) {
        let imgUrl = `https://i.udemycdn.com/user/200_H/anonymous_3.png`;
        if (!(!item.image))
            imgUrl = "data:image/png;base64," + item.image;
        content += `
            <div class="col-md-3">
                <a href="details.html?id=${item.id}" class="my-course-item">
                    <img src="${imgUrl}" alt="">
                    <h6 class="my-course-title">${item.title}</h6>
                    <div class="my-course-desc">
                        Java Python Android and C# Expert Developer - 467K+ students The Complete Guide (incl Hooks,
                        React Router, Redux)
                    </div>
                    <div class="my-course-author">
                        <h6>
                            <small>Lê Thiện</small>
                            <small>Start course</small>
                        </h6>
                    </div>
                </a>
            </div>
        `;
    }

    tbody.innerHTML = content;
};
setCourse();