//loadUserInfor();
let getResult = function () {
    //urlParams.get("id");
    let urlParams = new URLSearchParams(window.location.search);
    let key = urlParams.get("key");
    console.log(key);
    axios({
        url: Url + `api/course/search-dto/${key}`,
        method: "GET"
    })
        //Xữ lý mã trạng thái bắt đầu bằng số 2
        .then(function (response) {
            //Truy xuất đến thẻ body( nơi sẽ chứa giao diện)
            console.log(response.data);
            let courseSearch = document.getElementById("searchContent");
            let contentSearch = `
            <section class="course-banner">
        <div class="container">
            <div class="banner-content">
                <h1> Result of '${key}'</h1>
            </div>
        </div>
    </section>
    <section class="mt-5">
        <div class="container">
            <div class="row" >
            `;
            //Thay đổi nội dung thẻ tbody
            //let imgUrl = `https://i.udemycdn.com/user/200_H/anonymous_3.png`;

            for (let item of response.data) {
                if (item.discount > 0) {
                    let price = String(item.price).replace(/(.)(?=(\d{3})+$)/g, '$1,');
                    let promotionPrice = String(item.promotionPrice).replace(/(.)(?=(\d{3})+$)/g, '$1,');
                    let imgUrl = `https://i.udemycdn.com/user/200_H/anonymous_3.png`;
                    if (!(!item.image))
                        imgUrl = "data:image/png;base64," + item.image;
                    contentSearch += `
                <div class="col-md-3">
                <div class="course">
                    <img src="${imgUrl}" />
                    <h6 class="course-title">${item.title}</h6>
                    <small class="course-content">
                        Become an ethical hacker that can hack computer systems like black hat hackers and
                        secure
                        them like security experts.
                    </small>
                    <div class="course-price">
                        <span>${promotionPrice} đ</span>
                        <small>${price} đ</small>
                    </div>
                    <div class="seller-label">Sale ${item.discount}%</div>
                    <div class="course-overlay">
                        <a href="details.html?id=${item.id}">
                            <h6 class="course-title">
                            ${item.title}
                            </h6>
                            <div class="course-author">
                                <b>Lê Quang Song</b>
                                <span class="mx-1"> | </span>
                                <b>${item.categoryTitle}</b>
                            </div>
                            <div class="course-info">
                                <span><i class="fa fa-play-circle"></i> ${item.leturesCount} lectures</span>
                                <span class="mx-1"> | </span>
                                <span><i class="fa fa-clock-o"></i> 40 hours</span>
                            </div>
                            <small class="course-content">
                                Become an ethical hacker that can hack computer systems like black hat
                                hackers and
                                secure them like security experts.
                            </small>
                        </a>
                        <a href="#" class="btn btn-sm btn-danger text-white w-100">Add to cart</a>
                    </div>
                </div>
            </div>
           `;
                } else {
                    let promotionPrice = String(item.promotionPrice).replace(/(.)(?=(\d{3})+$)/g, '$1,');
                    let imgUrl = `https://i.udemycdn.com/user/200_H/anonymous_3.png`;
                    if (!(!item.image))
                        imgUrl = "data:image/png;base64," + item.image;
                    contentSearch += `
                <div class="col-md-2">
                    <div class="course">
                        <img src="${imgUrl}" />
                        <h6 class="course-title">${item.title}</h6>
                        <small class="course-content">
                            Become an ethical hacker that can hack computer systems like black hat hackers and secure
                            them like security experts.
                        </small>
                        <div class="course-price">
                            <span>${promotionPrice} đ</span>
                        </div>
                        <div class="course-overlay">
                            <a href="details.html?id=${item.id}">
                                <h6 class="course-title">
                                ${item.title}
                                </h6>
                                <div class="course-author">
                                    <b>Lê Quang Song</b>
                                    <span class="mx-1"> | </span>
                                    <b>${item.categoryTitle}</b>
                                </div>
                                <div class="course-info">
                                    <span><i class="fa fa-play-circle"></i> ${item.leturesCount} lectures</span>
                                    <span class="mx-1"> | </span>
                                    <span><i class="fa fa-clock-o"></i> 40 hours</span>
                                </div>
                                <small class="course-content">
                                    Become an ethical hacker that can hack computer systems like black hat hackers and
                                    secure them like security experts.
                                </small>
                            </a>
                            <a href="#" class="btn btn-sm btn-danger text-white w-100">Add to cart</a>
                        </div>
                    </div>
                </div>
                `;
                }
            }
            contentSearch+=`
            </div>
            </div>
        </section>
            `;
            courseSearch.innerHTML = contentSearch;
            
        })
        //Xữ lý mã trạng thái còn lại
        .catch(function (e) {
            console.log(e.response)
        });
}
getResult();