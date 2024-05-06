//loadUserInfor();
var user = JSON.parse(localStorage.getItem('USER_INFOR'));
let token = localStorage.getItem("USER_TOKEN");
// console.log(user);
//Nếu token null hoặc rỗng (chưa đăng nhập)
//if (!user) {  
//    window.location.href = "index.html";
//}
let urlParams = new URLSearchParams(window.location.search);
let courseId = urlParams.get("id");

let loadUrlVideo = function (urlVideo) {
    document.getElementById("urlVideo").setAttribute("src", urlVideo);
}

//type = 1 => xx hours xx minutes
//type = 2 => hh:mm:ss
let converTimeOfCourse = function(timeCount, type){
    let time_count;
    var hours = Math.floor(timeCount / 3600);
    var minute = Math.floor((timeCount % 3600) / 60);
    var seconds = (timeCount % 3600) % 60;
    if(type===1){
        if (hours === 0) {
            time_count = ("0" + minute).slice(-2) + " minutes";
        } else if (hours >= 100) {
            time_count = ("0" + hours).slice(-3) + " hours " + ("0" + minute).slice(-2) + " minutes";
        }
        else {
            time_count = ("0" + hours).slice(-2) + " hours " + ("0" + minute).slice(-2) + " minutes";
        }
    } else if ( type === 2){
        if (hours === 0) {
            time_count = ("0" + minute).slice(-2) + ":" + ("0" + seconds).slice(-2);
        } else if (hours >= 100) {
            time_count = ("0" + hours).slice(-3) + ":" + ("0" + minute).slice(-2) + ":" + ("0" + seconds).slice(-2);
        }
        else {
            time_count = ("0" + hours).slice(-2) + ":" + ("0" + minute).slice(-2) + ":" + ("0" + seconds).slice(-2);
        }
    }
    return time_count;
}

// ownCheck = false => Chưa mua khoá học
// ownCheck = true => Đã mua khoá h
let addContentDetail = function (course, ownCheck) {
    //Title khoá học
    document.getElementById("bannerDetailTitle").innerHTML = course.title;
    //Ngày update khoá học
    const d = new Date(course.lastUpdate);
    const ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d);
    const mo = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(d);
    const da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d);  
    document.getElementById("detailLastUpdate").innerHTML = `Last updated ${da}-${mo}-${ye}`;
    //Số lượng video của khoá học
    document.getElementById("detailLetures").innerHTML = course.leturesCount + " lectures";
    document.getElementById("lecture-count-course").innerHTML = course.leturesCount + " lectures";
    // Desc của khoá học
    document.getElementById("bannerDetailDesc").innerHTML = course.description;
    // Content của khoá học
    if (course.content) {
        document.getElementById("detailCourseContent").innerHTML = course.content.replace(/(?:\\[rn]|[\r\n]+)+/g, "<br/>");
    }
    //Thời gian của khoá học
    document.getElementById("detailHours").innerHTML = converTimeOfCourse(course.hourCount, 1);
    //Target của khoá học
    let targetLeft = document.getElementById("detailTargetsLeft");
    let targetRight = document.getElementById("detailTargetsRight");
    let check = 1;
    let contentLeft = '';
    let contentRight = '';
    for (let item of course.targets) {
        if (check % 2 == 0) {
            contentRight += `
                <li>
                    <i class="fa fa-check"></i>
                    <span>${item.title}</span>
                </li>
                `;
        } else {
            contentLeft += `
                <li>
                    <i class="fa fa-check"></i>
                    <span>${item.title}</span>
                </li>
                `;
        }
        check++;
    }
    targetLeft.innerHTML = contentLeft;
    targetRight.innerHTML = contentRight;
    //Course list video của khoá học
    document.getElementById("hour-count-course").innerHTML = converTimeOfCourse(course.hourCount, 2);
    let videoList = document.getElementById("list-content");
    let contentVideo = '';
    for (let item of course.videos) {
        let time_count = converTimeOfCourse(item.timeCount, 2);
        if (ownCheck === false) {
            contentVideo += `
            <li>
                <a >
                    <span> <i class="fa fa-play-circle mr-1"></i>
                        ${item.title}
                    </span>
                    <span>${time_count}</span>
                </a>
            </li>
            `;
        } else {
            contentVideo += `
            <li>
                <a href="#" data-toggle="modal" data-target="#myVideoModal" onclick="loadUrlVideo('${item.url}')" >
                    <span> <i class="fa fa-play-circle mr-1"></i>
                        ${item.title}
                    </span>
                    <span>${time_count}</span>
                </a>
            </li>
            `;
        }
    }
    videoList.innerHTML = contentVideo;
    //Giá của khoá học
    let price = String(course.price).replace(/(.)(?=(\d{3})+$)/g, '$1,');
    let promotionPrice = String(course.promotionPrice).replace(/(.)(?=(\d{3})+$)/g, '$1,');
    let detailBuy = `
    <div class="course-buy">
            <h2 class="mb-4 font-weight-bold">
                ${promotionPrice} đ
                <small>${price} đ</small>
            </h2>
            <button class="btn btn-danger w-100">Add to cart</button>
            <button class="btn btn-outline-secondary w-100" onclick="buy()">Buy now</button>
            <div class="course-buy-info mt-2">
                <span>This course includes</span>
                <small><i class="fa fa-play-circle-o"></i> 24 hours on-demand video</small>
                <small><i class="fa fa-file-o"></i> 19 articles</small>
                <small><i class="fa fa-code"></i> 19 coding exercises</small>
                <small><i class="fa fa-empire"></i> Full lifetime access</small>
                <small><i class="fa fa-tablet"></i> Access on mobile and TV</small>
                <small><i class="fa fa-recycle"></i> Certificate of Completion</small>
            </div>
            <a class="course-buy-share border-top" href="#">
                <i class="fa fa-share"></i> Share
            </a>
        </div>
    `;
    //Trường hợp chưa mua khoá học
    if (ownCheck === false) {
        document.getElementById("detailBuy").innerHTML = detailBuy;
    } 
    //Trường hợp đã mua khoá học
    else {
        document.getElementById("detailBuy").innerHTML = ``;
    }
}

let loadDetail = function () {
    //Trường hợp chưa login
    if (!user) {
        axios({
            url: Url +  `api/course/get-dto/${courseId}`,
            method: "GET"
        })
            .then(function (resp) {
                let course = resp.data;
                addContentDetail(course, false);
            })
            .catch(function (e) {
                console.log(e.resp)
            });
    } 
    //Trường hợp đã login
    else {
        axios({
            url: Url +  `api/user-course/get-course-by-user-id/${user.id}/${courseId}`,
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        })
        //Trường hợp đã mua khoá học
            .then(function (response) {
                axios({
                    url: Url +  `api/course/get-dto/${courseId}`,
                    method: "GET",
                    headers: {
                        "Authorization": "Bearer " + token
                    }
                })
                    .then(function (resp) {
                        let course = resp.data;
                        console.log(course);
                        addContentDetail(course, true);

                    })
                    .catch(function (e) {
                        console.log(e.resp)
                    });
            })
        //Trường hợp chưa mua khoá học
            .catch(function (e) {
                console.log(e.response)
                axios({
                    url: Url +  `api/course/get-dto/${courseId}`,
                    method: "GET",
                    headers: {
                        "Authorization": "Bearer " + token
                    }
                })
                    .then(function (resp) {
                        let course = resp.data;
                        addContentDetail(course, false);
                    })
                    .catch(function (e) {
                        console.log(e.resp)
                    });
            });
    }
}

let buy = function () {
    axios({
        url: Url +  "api/user-course/add",
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token
        },
        data: {
            courseId: courseId,
            userId: user.id,
            roleId: user.roleId
        }

    })
        .then(function (response) {
            loadUserInfor();
            loadDetail();
        })
        .catch(function (e) {
            console.log(e.response)
        });
}

loadDetail();