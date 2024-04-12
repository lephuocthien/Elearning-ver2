let getAllCourse = function () {
    axios({
        url: "http://localhost:8087/api/course",
        method: "GET"
    })
        //Xữ lý mã trạng thái bắt đầu bằng số 2
        .then(function (response) {
            //Truy xuất đến thẻ body( nơi sẽ chứa giao diện)
            console.log(response.data);
            let courseSale = document.getElementById("courseSale");
            let coursePopular = document.getElementById("coursePopular");
            let contentSale = '';
            let contentPopular = '';
            //Thay đổi nội dung thẻ tbody
            let imgUrl = `https://i.udemycdn.com/user/200_H/anonymous_3.png`;

            for (let item of response.data) {
                if (item.discount > 0) {
                    let price = String(item.price).replace(/(.)(?=(\d{3})+$)/g, '$1,');
                    let promotionPrice = String(item.promotionPrice).replace(/(.)(?=(\d{3})+$)/g, '$1,');
                    if (!(!item.image))
                        imgUrl = `http://localhost:8087/api/course/file/load/${item.image}`;
                    contentSale += `
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
                }
                let promotionPrice = String(item.promotionPrice).replace(/(.)(?=(\d{3})+$)/g, '$1,');
                if (!(!item.image))
                    imgUrl = `http://localhost:8087/api/course/file/load/${item.image}`;
                contentPopular += `
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
                courseSale.innerHTML = contentSale;
                coursePopular.innerHTML = contentPopular;
            }
        })
        //Xữ lý mã trạng thái còn lại
        .catch(function (e) {
            console.log(e.response)
        });
}
getAllCourse();