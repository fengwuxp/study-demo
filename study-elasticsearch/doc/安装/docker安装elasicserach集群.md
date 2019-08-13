

#### docker安装

- 安装方式来源 [使用kubeadm安装Kubernetes 1.15](https://www.kubernetes.org.cn/5551.html)
- 安装docker的yum源:
```
 yum install -y yum-utils device-mapper-persistent-data lvm2
 yum-config-manager \
    --add-repo \
    https://download.docker.com/linux/centos/docker-ce.repo

```
- 查看最新的Docker版本：
```
 yum list docker-ce.x86_64  --showduplicates |sort -r
 docker-ce.x86_64            3:18.09.7-3.el7                     docker-ce-stable
 docker-ce.x86_64            3:18.09.6-3.el7                     docker-ce-stable
 docker-ce.x86_64            3:18.09.5-3.el7                     docker-ce-stable
 docker-ce.x86_64            3:18.09.4-3.el7                     docker-ce-stable
 docker-ce.x86_64            3:18.09.3-3.el7                     docker-ce-stable
 docker-ce.x86_64            3:18.09.2-3.el7                     docker-ce-stable
 docker-ce.x86_64            3:18.09.1-3.el7                     docker-ce-stable
 docker-ce.x86_64            3:18.09.0-3.el7                     docker-ce-stable
 docker-ce.x86_64            18.06.3.ce-3.el7                    docker-ce-stable
 docker-ce.x86_64            18.06.2.ce-3.el7                    docker-ce-stable
 docker-ce.x86_64            18.06.1.ce-3.el7                    docker-ce-stable
 docker-ce.x86_64            18.06.0.ce-3.el7                    docker-ce-stable
 docker-ce.x86_64            18.03.1.ce-1.el7.centos             docker-ce-stable
 docker-ce.x86_64            18.03.0.ce-1.el7.centos             docker-ce-stable
```
- 选中一个版本安装，例如
```
  yum makecache fast

  yum install -y --setopt=obsoletes=0 \
  docker-ce-18.09.7-3.el7 

  //启动docker
  systemctl start docker
  
  //将docker加入系统服务，并设置开机启动
  systemctl enable docker
  
```

### 安装docker compose
- [Docker — 从入门到实践](https://docker_practice.gitee.io/)
- [linux安装方式](https://docker_practice.gitee.io/compose/install.html)
```
curl -L https://github.com/docker/compose/releases/download/1.25.0-rc2/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose

//如果curl命令出现类似错误 Peer reports incompatible or unsupported protocol version.
则可以通过yum升级curl
```

### 通过docker compose安装elasticserach
```
 将本目录的docker-compose.yaml 复制到你想要的目录 
 
 #启动
 docker-compose up

 #停止容器
 docker-compose down

 #停止容器并且移除数据
 docker-compose down -v

 #一些docker 命令
 docker ps
 docker stop Name/ContainerId
 docker start Name/ContainerId

 #删除单个容器
 $docker rm Name/ID
 -f, –force=false; -l, –link=false Remove the specified link and not the underlying container; -v, –volumes=false Remove the volumes associated to the container

 #删除所有容器
 $docker rm `docker ps -a -q`  
 停止、启动、杀死、重启一个容器
 $docker stop Name/ID  
 $docker start Name/ID  
 $docker kill Name/ID  
 $docker restart name/ID

```
- 安装出错指引
```
 1.如果出现内存不够的提示，加大内存设置（虚拟机）
 2.max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
 编辑 /etc/sysctl.conf，追加以下内容：vm.max_map_count=262144保存后，执行：sysctl -p，让后重新启动
```
- 启动成功后访问对应的端口
```
 kibana: http://127.0.0.1:5601
 elasticsearch: http://127.0.0.1:9200
 cerebro: http://127.0.0.1:9000
```
