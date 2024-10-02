# Use an official OpenJDK image as a parent image
FROM openjdk:8-jdk

# Install required packages
RUN apt-get update && \
    apt-get install -y wget unzip && \
    apt-get clean

# Set environment variables for Android SDK
ENV ANDROID_SDK_VERSION=4333796
ENV ANDROID_SDK_URL=https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip
ENV ANDROID_HOME=/opt/android-sdk

# Create directory for Android SDK
RUN mkdir -p ${ANDROID_HOME} && cd ${ANDROID_HOME} && \
    wget ${ANDROID_SDK_URL} && \
    unzip commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip && \
    rm commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip && \
    yes | ./cmdline-tools/bin/sdkmanager --sdk_root=${ANDROID_HOME} --install "platform-tools" "platforms;android-33" "build-tools;30.0.3"

# Set PATH for Android SDK
ENV PATH=${PATH}:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/cmdline-tools/latest/bin

# Set working directory
WORKDIR /app

# Copy the project files into the Docker image
COPY . .

# Build the project
RUN ./gradlew build
