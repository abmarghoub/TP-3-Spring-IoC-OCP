# TP 3 : Spring IoC & POC

---
### Réalisé par

**Abla MARGHOUB**

### Encadré par

**Pr. Mohamed LACHGAR**

### Module

**Techniques de Programmation Avancée**

### Établissement

**École Normale Supérieure - Université Cadi Ayyad**

---

## Objectif

- Mettre en place plusieurs stratégies d’injection et de sélection d’implémentations `IDao` sans modifier la classe métier `MetierImpl` ni la classe d’exécution.  
- Respecter le principe OCP (Open/Closed Principle).  
- Explorer différents mécanismes pour gérer la dépendance :  
  - Profils Spring (`dev`, `prod`, `file`, `api`)  
  - Priorité via `@Primary`  
  - Alias via un `@Bean` de configuration  
  - Sélection par propriété externe (`@PropertySource` + `@Value`)  

---

## Description des interfaces et classes

| Interface / Classe       | Package       | Rôle et Description                                                                 |
|--------------------------|--------------|-----------------------------------------------------------------------------------|
| `IDao`                   | dao          | Interface pour la récupération d’une valeur (`getValue()`)                        |
| `DaoImpl`                | dao          | Implémentation de `IDao` pour le profil `prod`, retourne 100.0                     |
| `DaoImpl2`               | dao          | Implémentation de `IDao` pour le profil `dev`, retourne 150.0                     |
| `DaoFile`                | dao          | Implémentation de `IDao` pour le profil `file`, retourne 180.0                    |
| `DaoApi`                 | dao          | Implémentation de `IDao` pour le profil `api`, retourne 220.0                     |
| `IMetier`                | metier       | Interface métier définissant la méthode `calcul()`                                 |
| `MetierImpl`             | metier       | Classe métier avec `@Autowired` IDao injecté, calcul = `dao.getValue() * 2`       |
| `Presentation2`          | presentation | Classe principale : configuration Spring, scan des packages, récupération du bean `IMetier` |
| `DaoAliasConfig`         | config       | Bean alias pour sélectionner une implémentation spécifique de `IDao`               |
| `PropertyDrivenConfig`   | config       | Bean `IDao` sélectionné dynamiquement via fichier de propriétés                    |
| `app.properties`         | resources    | Fichier de configuration pour sélectionner l’implémentation via propriété         |

---

## Fonctionnement global

1. **Injection de dépendances via Spring** : `MetierImpl` reçoit un bean `IDao` injecté automatiquement.  
2. **Sélection de l’implémentation** :  
   - Par **profils Spring** (`@Profile`) pour activer un bean selon l’environnement (`dev`, `prod`, `file`, `api`).  
   - Par **@Primary** pour choisir un bean par défaut si plusieurs sont disponibles.  
   - Par **alias** avec `@Bean` dans `DaoAliasConfig` pour rediriger vers une implémentation spécifique.  
   - Par **propriété externe** avec `PropertyDrivenConfig` pour sélectionner dynamiquement un bean via `app.properties`.  
3. **Exécution** : la classe `Presentation2` initialise le contexte Spring, sélectionne le bean `IMetier`, appelle la méthode `calcul()` et affiche le résultat.  
4. **Tests de non-régression** : JUnit 4 pour vérifier que chaque configuration retourne la valeur attendue sans modifier `MetierImpl`.

---

## Résultats attendus

---

**Exécution avec Profil Spring `dev`**

<img width="578" height="672" alt="41" src="https://github.com/user-attachments/assets/39bb4066-1c7c-4eba-a567-288151058ae4" />

**Exécution avec Profil Spring `file`**

<img width="633" height="711" alt="42" src="https://github.com/user-attachments/assets/2aaa22ea-30fb-420e-b40a-9897919ecc73" />

**Exécution avec Profil Spring `api`**

<img width="683" height="724" alt="44" src="https://github.com/user-attachments/assets/7cd38e89-f948-4158-84c0-54b16395fa59" />

**Exécution avec dao.target**

<img width="566" height="723" alt="43" src="https://github.com/user-attachments/assets/de68a20e-c218-48fb-bc8d-a0d74445ffa9" />

