# MobStackerPremium

**MobStackerPremium** est un plugin Spigot conçu pour améliorer les performances de votre serveur Minecraft en réduisant le nombre d'entités via un système d'empilement des mobs. Ce plugin est entièrement personnalisable et offre des commandes puissantes pour gérer et surveiller les statistiques des mobs empilés.

## Fonctionnalités

- **Empilement des mobs** : Combine automatiquement les mobs similaires lorsqu'ils se trouvent dans un rayon défini, réduisant ainsi le nombre total d'entités.
- **Personnalisation avancée** :
  - Définissez un rayon d'empilement.
  - Limitez la taille maximale des piles.
  - Excluez certains types de mobs de l'empilement (par exemple, les villageois).
- **Statistiques détaillées** :
  - Affichez les statistiques des mobs empilés par monde et par type.
  - Calculez le gain de performance en pourcentage.
- **Commandes puissantes** :
  - Rechargez la configuration sans redémarrer le serveur.
  - Donnez aux joueurs un bâton spécial pour forcer l'empilement manuel des mobs dans une zone définie.
- **Optimisation asynchrone** : Le plugin est conçu pour fonctionner de manière asynchrone afin d'éviter tout impact sur la stabilité du serveur.

---

## Installation

1. Téléchargez le fichier JAR du plugin.
2. Placez-le dans le dossier `plugins` de votre serveur Spigot.
3. Redémarrez votre serveur pour générer les fichiers de configuration.
4. Personnalisez le fichier `config.yml` selon vos besoins.

---

## Commandes

| Commande                 | Description                                                                 | Permission                   |
|--------------------------|-----------------------------------------------------------------------------|------------------------------|
| `/mobstacker`            | Affiche l'aide du plugin.                                                  | Aucune                       |
| `/mobstacker help`       | Affiche l'aide du plugin.                                                  | Aucune                       |
| `/mobstacker stats`      | Affiche les statistiques des mobs empilés par monde et type.               | Aucune                       |
| `/mobstacker reload`     | Recharge la configuration du plugin.                                       | `mobstackerpremium.reload`   |
| `/mobstacker stick`      | Donne un bâton spécial permettant de forcer l'empilement manuel des mobs.  | `mobstackerpremium.stick`    |

---

## Permissions

| Permission                   | Description                                      |
|------------------------------|--------------------------------------------------|
| `mobstackerpremium.reload`   | Permet de recharger la configuration du plugin. |
| `mobstackerpremium.stick`    | Permet d'obtenir le bâton de stacking.          |

---

## Configuration (`config.yml`)

Voici un exemple de configuration par défaut :

```yaml
# MobStacker Premium Configuration

# Taille maximale d'une pile
max-stack-size: 50

# Rayon pour empiler automatiquement les mobs
stack-radius: 5.0

# Liste des types de mobs exclus de l'empilement
excluded-mobs:
  - VILLAGER
  - WITHER
  - ENDER_DRAGON

messages:
  stick-success: "&aLes mobs proches ont été empilés !"
```

---

## Fonctionnement du bâton de stacking

Le bâton de stacking est un outil spécial qui permet aux joueurs de forcer manuellement l'empilement des mobs dans une zone définie.

### Comment obtenir le bâton ?

Utilisez la commande suivante (nécessite la permission `mobstackerpremium.stick`) :

```
/mobstacker stick
```

### Comment utiliser le bâton ?

1. Tenez le bâton dans votre main principale.
2. Faites un clic droit dans une zone contenant plusieurs mobs.
3. Les mobs proches seront automatiquement empilés.

---

## Statistiques

Utilisez `/mobstacker stats` pour afficher les informations suivantes :

- Gain de performance en pourcentage (basé sur le nombre total d'entités réduites).
- Nombre total de mobs empilés par monde et par type.

Exemple de sortie :

```
=== Statistiques des mobs empilés ===
Gain de performance : 35.67%
Statistiques par monde :
- Monde : world
  * ZOMBIE: 120
  * SKELETON: 80
- Monde : nether
  * PIGLIN: 50
```

---

## Auteur

Plugin développé par **OxiWan**.
