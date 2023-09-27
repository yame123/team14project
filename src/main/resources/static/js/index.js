const host = 'http://' + window.location.host;

// 홈으로 가기 버튼
function goHome(){
    location.href="/";
}

// 로그아웃
function logout() {
    // 토큰 삭제
    Cookies.remove('Authorization', {path: '/'});
    window.location.href = host + '/user/login-page';
}

// $(document).ready(function () {
//     const auth = getToken();
//
//     if (auth !== undefined && auth !== '') {
//         $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
//             jqXHR.setRequestHeader('Authorization', auth);
//         });
//     } else {
//         window.location.href = host + '/user/login-page';
//         return;
//     }
//     console.log(getToken());
// });

// 쿠키에서 유저 정보를 읽어옵니다.
function getToken() {
    let auth = Cookies.get('Authorization');
    if (auth === undefined) {
        return '';
    }

    // kakao 로그인 사용한 경우 Bearer 추가
    if (auth.indexOf('Bearer') === -1 && auth !== '') {
        auth = 'Bearer ' + auth;
    }
    return auth;
}

// 메뉴 클릭시 메뉴 상세 페이지 이동
function goMenuDetail(){
    location.href="../../templates/menu/menuDetails.html";
}

// 주문 수량 카운트
function count(type) {
    const resultElement = document.getElementById('cnt');
    
    let number = resultElement.innerText;

    if(type === 'plus') {
      number = parseInt(number) + 1;
    }else if(type === 'minus')  {
      number = parseInt(number) - 1;
    }

    resultElement.innerText = number;
}

// 상점 상세 페이지 탭 관련
function openTab(tabName) {
    var i, tabContent;

    // 탭 숨기기
    tabContent = document.getElementsByClassName("tab-content");
    for (i = 0; i < tabContent.length; i++) {
        tabContent[i].style.display = "none";
    }

    // 누른 탭 내용 보이기
    document.getElementById(tabName).style.display = "block";
}

// 평점 별 꾸미기
function rateMenu(rating) {
    selectedRating = rating;
    const stars = document.querySelectorAll('.star');
    stars.forEach((star, index) => {
        if (index < rating) {
            star.classList.add('selected');
        } else {
            star.classList.remove('selected');
        }
    });
}

// 버튼 합치기
function mergeButtons() {
    const button1 = document.getElementById('button1');
    const button2 = document.getElementById('button2');
    const mergedButton = document.getElementById('mergedButton');

    if (button2.style.display !== 'none') {
        button1.style.display = 'none';
        button2.style.display = 'none';
        mergedButton.style.display = 'inline-block';
    }
}

// 주문 취소 버튼 누를 때
function cancelOrder() {
    const button1 = document.getElementById('button1');
    const text = document.getElementById('orderStatusText');

    if (button1.style.display !== 'none') {
        button1.style.display = 'none';
        button2.style.display = 'none';
        homeButton.style.display = 'inline-block';
        text.textContent = '취소된 주문 입니다.';
        text.style.display = 'inline-block';
    }
}

// 배달 완료 버튼 누를 때
function completeOrder() {
    const mergedButton = document.getElementById('mergedButton');
    const text = document.getElementById('orderStatusText');

    if (mergedButton.style.display !== 'none') {
        mergedButton.style.display = 'none';
        homeButton.style.display = 'inline-block';
        text.textContent = '배달 완료된 주문 입니다.';
        text.style.display = 'inline-block';
    }
}