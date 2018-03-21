echo " cp /PROJECT/ASP20059/DEPLOY/PDEApp.ear  pd_deployment_dir/backup/PDEApp_UD_`date +%Y-%m-%d-%H_%M_%S`.ear "
cp /PROJECT/ASP20059/DEPLOY/PDEApp.ear  pd_deployment_dir/backup/PDEApp_UD_`date +%Y-%m-%d-%H_%M_%S`.ear
echo " cp PDEApp.ear pd_deployment_dir/backup/PDEApp_D_`date +%Y-%m-%d-%H_%M_%S`.ear "
cp PDEApp.ear pd_deployment_dir/backup/PDEApp_D_`date +%Y-%m-%d-%H_%M_%S`.ear
echo " cp PDEApp.ear /PROJECT/ASP20059/DEPLOY/PDEApp.ear "
cp PDEApp.ear /PROJECT/ASP20059/DEPLOY/PDEApp.ear
echo " ./jboss_stop.sh 1 "
./jboss_stop.sh 1
echo " ./remove.sh PDEApp.ear 1 "
./remove.sh PDEApp.ear 1
echo " ./undeploy.sh PDEApp.ear "
./undeploy.sh PDEApp.ear
echo " ./deploy.sh PDEApp.ear "
./deploy.sh PDEApp.ear
echo " ./assign.sh PDEApp.ear 1 "
./assign.sh PDEApp.ear 1
echo " ./jboss_start.sh all 1 "
./jboss_start.sh all
echo " ./cl_stop.sh 1 "
./cl_stop.sh 1
echo " ./cl_start.sh 1 "
./cl_start.sh 1
