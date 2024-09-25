$containerName = "mssql_container"
$imageName = "mcr.microsoft.com/mssql/server"
$adminPassword = "JubarteMagrathiana42!"

Write-Host "Criando e iniciando o container do SQL Server..."
docker run `
    -e "ACCEPT_EULA=Y" `
    -e "SA_PASSWORD=$adminPassword" `
    -p 1433:1433 `
    --name $containerName `
    -d `
    $imageName

Write-Host "Status do container:"
docker ps -a | Select-String $containerName
