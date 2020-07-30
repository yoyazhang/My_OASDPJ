//页面加载完毕后执行
window.onload = function () {
    let smallBox = document.getElementById("smallBox");
    let smallBoxImage = smallBox.getElementsByTagName("img")[0];
    let floatBox = document.getElementById("floatBox");
    let bigBox = document.getElementById("bigBox");
    let bigBoxImage = bigBox.getElementsByTagName("img")[0];

    bigBoxImage.style.width = 2.5 * smallBoxImage.offsetWidth + "px";

    //鼠标进入图片，放大镜出现
    smallBox.onmouseover = function () {
        floatBox.style.display = "block";
        bigBox.style.display = "block";
    };
    //鼠标移出图片则放大镜直接从页面上消失
    smallBox.onmouseout = function () {
        floatBox.style.display = "none";
        bigBox.style.display = "none";
    };
    //鼠标移动事件
    smallBox.onmousemove = function (e) {
        let left = e.pageX - smallBox.offsetLeft - floatBox.offsetWidth / 2;
        let top = e.pageY - smallBox.offsetTop - floatBox.offsetHeight / 2;

        left = Math.max(left, 0);
        left = Math.min(left, smallBox.offsetWidth - floatBox.offsetWidth);

        top = Math.max(top, 0);
        top = Math.min(top, smallBox.offsetHeight - floatBox.offsetHeight);
        //实现放大镜随着鼠标移动
        floatBox.style.left = left + "px";
        floatBox.style.top = top + "px";
        //现有位置与可能达到的最大位置之比(可理解为位置移动了百分之多少)
        let percentX = left / (smallBoxImage.offsetWidth - floatBox.offsetWidth);
        let percentY = top / (smallBoxImage.offsetHeight - floatBox.offsetHeight);
        //用相同的比例*可能的最大位置，由于放大镜实现原理，反向变动
        bigBoxImage.style.left = -percentX * (bigBoxImage.offsetWidth - bigBox.offsetWidth) + "px";
        bigBoxImage.style.top = -percentY * (bigBoxImage.offsetHeight - bigBox.offsetHeight) + "px";
    }
};