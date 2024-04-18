var user = JSON.parse(localStorage.getItem('USER_INFOR'));
let token = localStorage.getItem("USER_TOKEN");
// console.log(user);
//Nếu token null hoặc rỗng (chưa đăng nhập)
//if (!user) {  
//    window.location.href = "index.html";
//}
loadUserInfor();
let urlParams = new URLSearchParams(window.location.search);
let courseId = urlParams.get("id");

let testVideo = function (urlVideo) {
    document.getElementById("urlVideo").setAttribute("src", urlVideo);
}
let loadDetail = function(){
    if (!user) {
        axios({
            url: `http://localhost:8087/api/course/get-dto/${courseId}`,
            method: "GET"
        })
            .then(function (resp) {
                let course = resp.data;
                console.log(course);
                const d = new Date(course.lastUpdate);
                const ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d);
                const mo = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(d);
                const da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d);
                document.getElementById("bannerDetailTitle").innerHTML = course.title;
                document.getElementById("detailLastUpdate").innerHTML = `Last updated ${da}-${mo}-${ye}`;
                document.getElementById("detailLetures").innerHTML = course.leturesCount + " lectures";

                let targetLeft = document.getElementById("detailTargetsLeft");
                let targetRight = document.getElementById("detailTargetsRight");
                //Thay đổi nội dung thẻ tbody
                let check = 1;
                let contentLeft = '';
                let contentRight = '';
                for (let item of course.targets) {
                    if (check % 2 == 0) {
                        // console.log(check);
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

                // var myNumber = 0;
                // var formattedNumber = ("0" + myNumber).slice(-2);
                // console.log(formattedNumber);

                let videoList = document.getElementById("list-content");
                let contentVideo = '';
                for (let item of course.videos) {
                    // console.log(item);
                    var hours = Math.floor(item.timeCount/3600);
                    // console.log(hours);
                    var minute =Math.floor((item.timeCount%3600)/60);
                    var seconds = (item.timeCount%3600)%60;
                    let time_count;
                    if(hours===0){     
                        time_count = ("0" + minute).slice(-2) + ":" +("0" + seconds).slice(-2);
                        // console.log(time_count);
                    } else{
                        time_count = ("0" + hours).slice(-2) + ":" +("0" + minute).slice(-2) + ":" +("0" + seconds).slice(-2);
                        // console.log(time_count);
                    }
                    // console.log(item.title);
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
                }
                // console.log(contentVideo);
                videoList.innerHTML = contentVideo;
                let price = String(course.price).replace(/(.)(?=(\d{3})+$)/g,'$1,');
                let promotionPrice = String(course.promotionPrice).replace(/(.)(?=(\d{3})+$)/g,'$1,');
                document.getElementById("detailBuy").innerHTML=`
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
            })
            .catch(function (e) {
                console.log(e.resp)
            });
    }
    else {
        //debugger
        axios({
            url: `http://localhost:8087/api/user-course/get-course-by-user-id/${user.id}/${courseId}`,
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token
            }
        })
            //Xữ lý mã trạng thái bắt đầu bằng số 2
            .then(function (response) {
                //Truy xuất đến thẻ body( nơi sẽ chứa giao diện)
                console.log(response.data);
                axios({
                    url: `http://localhost:8087/api/course/get-dto/${courseId}`,
                    method: "GET",
                    headers: {
                        "Authorization": "Bearer " + token
                    },
                })
                    .then(function (resp) {
                        let course = resp.data;
                        console.log(course);
                        const d = new Date(course.lastUpdate);
                        const ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d);
                        const mo = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(d);
                        const da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d);
                        document.getElementById("bannerDetailTitle").innerHTML = course.title;
                        document.getElementById("detailLastUpdate").innerHTML = `Last updated ${da}-${mo}-${ye}`;
                        document.getElementById("detailLetures").innerHTML = course.leturesCount + " lectures";
        
                        let targetLeft = document.getElementById("detailTargetsLeft");
                        let targetRight = document.getElementById("detailTargetsRight");
                        //Thay đổi nội dung thẻ tbody
                        let check = 1;
                        let contentLeft = '';
                        let contentRight = '';
                        for (let item of course.targets) {
                            if (check % 2 == 0) {
                                // console.log(check);
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
        
                        // var myNumber = 0;
                        // var formattedNumber = ("0" + myNumber).slice(-2);
                        // console.log(formattedNumber);
        
                        let videoList = document.getElementById("list-content");
                        let contentVideo = '';
                        for (let item of course.videos) {
                            // console.log(item);
                            var hours = Math.floor(item.timeCount/3600);
                            // console.log(hours);
                            var minute =Math.floor((item.timeCount%3600)/60);
                            var seconds = (item.timeCount%3600)%60;
                            let time_count;
                            if(hours===0){     
                                time_count = ("0" + minute).slice(-2) + ":" +("0" + seconds).slice(-2);
                                // console.log(time_count);
                            } else{
                                time_count = ("0" + hours).slice(-2) + ":" +("0" + minute).slice(-2) + ":" +("0" + seconds).slice(-2);
                                // console.log(time_count);
                            }
                            // console.log(item.title);
                            contentVideo += `
                                        <li>
                                            <a href="#" data-toggle="modal" data-target="#myVideoModal" onclick="testVideo('${item.url}')" >
                                                <span> <i class="fa fa-play-circle mr-1"></i>
                                                    ${item.title}
                                                </span>
                                                <span>${time_count}</span>
                                            </a>
                                        </li>
                            `;
                        }
                        // console.log(contentVideo);
                        videoList.innerHTML = contentVideo;
                        document.getElementById("detailBuy").innerHTML=``;
                    })
                    .catch(function (e) {
                        console.log(e.resp)
                    });
            })
            //Xữ lý mã trạng thái còn lại
            .catch(function (e) {
                console.log(e.response)
                axios({
                    url: `http://localhost:8087/api/course/get-dto/${courseId}`,
                    method: "GET",
                    headers: {
                        "Authorization": "Bearer " + token
                    }
                })
                    .then(function (resp) {
                        let course = resp.data;
                        console.log(course);
                        const d = new Date(course.lastUpdate);
                        const ye = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d);
                        const mo = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(d);
                        const da = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d);
                        document.getElementById("bannerDetailTitle").innerHTML = course.title;
                        document.getElementById("detailLastUpdate").innerHTML = `Last updated ${da}-${mo}-${ye}`;
                        document.getElementById("detailLetures").innerHTML = course.leturesCount + " lectures";
        
                        let targetLeft = document.getElementById("detailTargetsLeft");
                        let targetRight = document.getElementById("detailTargetsRight");
                        //Thay đổi nội dung thẻ tbody
                        let check = 1;
                        let contentLeft = '';
                        let contentRight = '';
                        for (let item of course.targets) {
                            if (check % 2 == 0) {
                                // console.log(check);
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
        
                        // var myNumber = 0;
                        // var formattedNumber = ("0" + myNumber).slice(-2);
                        // console.log(formattedNumber);
        
                        let videoList = document.getElementById("list-content");
                        let contentVideo = '';
                        for (let item of course.videos) {
                            // console.log(item);
                            var hours = Math.floor(item.timeCount/3600);
                            // console.log(hours);
                            var minute =Math.floor((item.timeCount%3600)/60);
                            var seconds = (item.timeCount%3600)%60;
                            let time_count;
                            if(hours===0){     
                                time_count = ("0" + minute).slice(-2) + ":" +("0" + seconds).slice(-2);
                                // console.log(time_count);
                            } else{
                                time_count = ("0" + hours).slice(-2) + ":" +("0" + minute).slice(-2) + ":" +("0" + seconds).slice(-2);
                                // console.log(time_count);
                            }
                            // console.log(item.title);
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
                        }
                        // console.log(contentVideo);
                        videoList.innerHTML = contentVideo;
                        let price = String(course.price).replace(/(.)(?=(\d{3})+$)/g,'$1,');
                        let promotionPrice = String(course.promotionPrice).replace(/(.)(?=(\d{3})+$)/g,'$1,');
                        document.getElementById("detailBuy").innerHTML=`
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
                    })
                    .catch(function (e) {
                        console.log(e.resp)
                    });
            });
    }
       
}
    let buy = function(){
        axios({
            url: "http://localhost:8087/api/user-course/add",
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
            //Xữ lý mã trạng thái bắt đầu bằng số 2
            .then(function (response) {
                //Truy xuất đến thẻ body( nơi sẽ chứa giao diện)
                loadUserInfor();
                loadDetail();
            })
            //Xữ lý mã trạng thái còn lại
            .catch(function (e) {
                console.log(e.response)
            });
    }
    loadDetail();