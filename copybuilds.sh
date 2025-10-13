# script to copy build files into one public-builds directory

mkdir -p publish-builds
cp versions/1.19.2/build/libs/tt20-0.7.2.jar publish-builds/tt20-0.7.2+mc1.19.2.jar
cp versions/1.20.1/build/libs/tt20-0.7.2.jar publish-builds/tt20-0.7.2+mc1.20.1.jar
cp versions/1.20.2/build/libs/tt20-0.7.2.jar publish-builds/tt20-0.7.2+mc1.20.2.jar
cp versions/1.20.3/build/libs/tt20-0.7.2.jar publish-builds/tt20-0.7.2+mc1.20.3.jar
cp versions/1.20.5/build/libs/tt20-0.7.2.jar publish-builds/tt20-0.7.2+mc1.20.5.jar
cp versions/1.21/build/libs/tt20-0.7.2.jar publish-builds/tt20-0.7.2+mc1.21.jar
cp versions/1.21.2/build/libs/tt20-0.7.2.jar publish-builds/tt20-0.7.2+mc1.21.2.jar
cp versions/1.21.9/build/libs/tt20-0.7.2.jar publish-builds/tt20-0.7.2+mc1.21.9.jar
