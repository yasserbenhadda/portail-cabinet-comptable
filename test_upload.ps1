# Script de test pour l'upload de document
Write-Host "Test de l'upload de document..." -ForegroundColor Green

# Test 1: Vérifier que l'API fonctionne
Write-Host "1. Test de l'API d'authentification..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8085/api/auth/test" -Method GET
    Write-Host "✅ API accessible: $($response.StatusCode)" -ForegroundColor Green
} catch {
    Write-Host "❌ Erreur API: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Créer un utilisateur test
Write-Host "2. Création d'un utilisateur test..." -ForegroundColor Yellow
$userData = @{
    nom = "Test User"
    email = "test@example.com"
    password = "password123"
    role = "CLIENT"
} | ConvertTo-Json

try {
    $response = Invoke-WebRequest -Uri "http://localhost:8085/api/auth/register" -Method POST -Body $userData -ContentType "application/json"
    Write-Host "✅ Utilisateur créé: $($response.StatusCode)" -ForegroundColor Green
    $user = $response.Content | ConvertFrom-Json
    Write-Host "ID utilisateur: $($user.id)" -ForegroundColor Cyan
} catch {
    Write-Host "❌ Erreur création utilisateur: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "3. Test de l'upload (nécessite un fichier)..." -ForegroundColor Yellow
Write-Host "Pour tester l'upload, utilise Postman avec:" -ForegroundColor Cyan
Write-Host "URL: http://localhost:8085/api/documents/upload" -ForegroundColor White
Write-Host "Méthode: POST" -ForegroundColor White
Write-Host "Body: form-data avec file, expediteurId, destinataireId, description" -ForegroundColor White 