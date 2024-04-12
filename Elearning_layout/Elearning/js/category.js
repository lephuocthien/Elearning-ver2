
let getAll = function(){
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
getAll();