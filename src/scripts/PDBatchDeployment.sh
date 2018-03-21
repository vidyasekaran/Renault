echo "cp application/appl-pgm/batch/jar/PDBatch.jar backup/PDBatch_UD_`date +%Y-%m-%d-%H_%M_%S`.jar"
cp application/appl-pgm/batch/jar/PDBatch.jar backup/PDBatch_UD_`date +%Y-%m-%d-%H_%M_%S`.jar
echo "cp PDBatch.jar backup/PDBatch_D_`date +%Y-%m-%d-%H_%M_%S`.jar"
cp PDBatch.jar backup/PDBatch_D_`date +%Y-%m-%d-%H_%M_%S`.jar
echo "PDBatch.jar application/appl-pgm/batch/jar/PDBatch.jar"
cp PDBatch.jar application/appl-pgm/batch/jar/PDBatch.jar
