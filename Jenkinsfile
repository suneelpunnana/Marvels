pipeline {
    agent any
    tools {
        maven "Maven"   
    }   
    environment{
        sonarscanner = tool 'SonarScanner'
    }
    stages {
        stage('Compile-Build-Test ') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('SonarQube Analysis'){
            steps{
               withSonarQubeEnv('sonarqube'){
                     sh '${sonarscanner}/bin/sonar-scanner -Dproject.settings=./sonar-project.properties'
                }
            }
        }
        stage("Quality Gate") {
            steps {
              timeout(time: 3, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
              }
            }
        }
        stage('Pushing to Nexus'){
            steps{
               withCredentials([usernamePassword(credentialsId: 'nexus-credentialss', passwordVariable: 'password', usernameVariable: 'username'),string(credentialsId: 'NEXUS_URL', variable: 'nexus_url')]){
                    //sh 'curl -u ${username}:${password} --upload-file target/BMIBeta-${BUILD_NUMBER}.jar http://18.224.155.110:8081/nexus/content/repositories/devopstraining/comrades/BMI-${BUILD_NUMBER}.war'
                    sh 'curl -v -F file=@target/BMI-0.war -u ${username}:${password} http://${nexus_url}/nexus/content/repositories/devopstraining/comrades/BMI-${BUILD_NUMBER}.war'
               }
            }
        }
        stage('Uploading yml to Ansible'){
            steps{
                sshPublisher(publishers: [sshPublisherDesc(configName: 'Ansible_server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: '', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '//opt//playbooks', remoteDirectorySDF: false, removePrefix: '', sourceFiles: 'project-ansible.yml')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: true)])   
                //sh 'scp -i /home/ansadmin/.ssh/id_rsa.pub project-ansible.yml ansadmin@172.31.22.13:/opt/playbooks'
            }
            
        }
        stage('Uploading artifacts and Executing Playbook'){
            steps{
                //sshPublisher(publishers: [sshPublisherDesc(configName: 'Ansible_server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'ansible-playbook /opt/playbooks/project-ansible.yml', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '//opt//playbooks', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '**/target/*.war')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: true)])
                sh 'sshpass -p "comrades" scp -i /home/ansadmin/.ssh/jen-ans.pub target/*.war ansadmin@172.31.22.13:/opt/playbooks/target'
            }
        }
        /*stage('Deployment to AWS'){
            steps{
            withCredentials([usernamePassword(credentialsId: 'tomcatCredentials', passwordVariable: 'password', usernameVariable: 'username'),string(credentialsId: 'TOMCAT_URL', variable: 'tomcat_url')]){
                    sh 'curl ${tomcat_url}/manager/text/undeploy?path=/BMI -u ${username}:${password}'
                    sh 'curl -v -u ${username}:${password} -T target/BMI${BUILD_NUMBER}.war ${tomcat_url}/manager/text/deploy?path=/BMI'
                }
            }
        }*/
      }
}
