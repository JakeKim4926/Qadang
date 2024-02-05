pipeline {
   agent any
   stages {
       stage('Gitlab') {
           steps {
               git branch: 'develop', url: 'https://lab.ssafy.com/s10-webmobile1-sub2/S10P12B106.git'
           }
       }
       stage('Build') {
           steps {
               dir('BE') {
                   sh 'pwd'
                   sh "./gradlew bootJar"
               }
           }
       }
       stage('Test') {
           steps {
               dir('BE') {
                   sh "./gradlew test"
               }
           }
       }
       stage('Deploy') {
           steps {
               dir('BE/build/libs') {
                   sshagent(credentials: ['EC2_SSH']) {
                        sh 'scp -o StrictHostKeyChecking=no BE-0.0.1-SNAPSHOT.jar ubuntu@172.26.7.51:/home/ubuntu'
                        sh 'ssh ubuntu@172.26.7.51 "sh run.sh" &'
                   }
               }
           }
       }
   }
}
