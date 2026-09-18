IMAGE_NAME="goguma1/calculator-gradle"
TAG="latest"

echo "이미지 $IMAGE_NAME:$TAG 다운로드 중..."
docker pull "$IMAGE_NAME:$TAG"

docker ps -q -f name=calculator | grep -q . && docker stop calculator

docker run -d -p 9000:9000 --rm --name calculator "$IMAGE_NAME:$TAG"

docker image prune -f

echo "작업 완료!!!"