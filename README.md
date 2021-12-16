# :iphone:SYM - Labo 3 - Environnement

:bust_in_silhouette: **Auteurs :** `Alexis Allemann` ,  `Hakim Balestrieri` et `Christian Gomes`

:page_facing_up: **En bref :** Ce repository contient la réalisation du laboratoire nommé "Environnement" de l'[HEIG-VD](https://heig-vd.ch/). Ce travail est réalisé durant le cours de SYM.

:dart: **Objectifs :** Ce travail est constitué de manipulations qui vont nous permettre de nous familiariser avec l’utilisation de données environnementales. Celui-ci est divisé en deux laboratoires : dans cette première partie nous nous intéresserons aux codes-barres, aux balises NFC et aux iBeacons

## Réponse aux questions:writing_hand:

### 2. NFC

#### 2.4.1 Clonage d'un tag NFC

Pour empêcher une personne malveillante de copier les valeurs stockées dans un tag NFC, il existes plusieurs méthodes :

De nombreuses étiquettes NFC contiennent également un identifiant unique qui est préprogrammé par le fabricant du tag et qui ne peut pas être modifié sur les tags normaux. Ainsi, il serait possible de créer une signature numérique basée sur l'identifiant du tag et ses données. Cependant, toutes les données peuvent être extraites de l'étiquette et il serait possible de créer une étiquette avec l'identifiant du tag en utilisant du matériel spécialisé (procédure relativement complexe).

Une autre solution plus sûre pourrait être l'utilisation d'un tag qui contient une clé asymétrique secrète et qui fournit une fonction pour signer un défi  cryptographique. Dans ce cas, pour vérifier l'authenticité du tag, on lui envoie un défi aléatoire qu'il doit signer et on vérifie le résultat du chiffrement.

Finalement, il existe une balise nommée `NTAG 413` qui peut générer un nouveau message NDEF à chaque fois qu'elle est utilisée (en utilisant un chiffrement AES). Cela permet d'incorporer le cryptage dans l'URL d'un NDEF et le serveur hôte peut le crypter avec la même clé. En cas de copie, le serveur le reconnaîtra. D'autres types de balises (ex : `NTAG 424`) utilisent le même principe.

Cela nécessite à un tiers (ici le serveur) d'authentifier les beacons.

> Source des informations trouvées : https://seritag.com/learn/using-nfc/nfc-tag-authentication-explained

#### 2.4.2 Utilisation d'un iBeacon

L'utilisation d'un iBeacon pour attester que l'utilisateur possède le tag est une solution peu efficace car le signal de l'iBeacon peut être lu par un attaquant et copié. En effet, il est très facile de simuler des iBeacons grâce à des applications mobiles. De plus, si l'attaquant se trouve à proximité du possesseur de l'iBeacon et qu'il est en mesure de recevoir les fréquences émises par ce dernier, alors il pourra alors tout de même s'authentifier.

### 3. QR-Codes

#### 3.2.1 Quantité maximale de données dans un QR-Code

Voici les limitations de tailles des données qu'un code QR peut contenir : 

| Type des données | Longueur maximale               |
| ---------------- | ------------------------------- |
| Numérique        | 7089                            |
| Alphanumérique   | 4296                            |
| Binaire / Byte   | 2953 (8-bit bytes) (23624 bits) |

> Spécification ISO d'un code QR : https://www.iso.org/standard/62021.html
>
> Source des informations trouvées : https://en.wikipedia.org/wiki/QR_code

Plus la taille des données stockées dans un code QR est grand, plus le code-barre devient complexe. La taille du code QR dépend des données qu'il contient. Si celui-ci en contient beaucoup, alors il est nécessaire d'afficher celui-ci en grand pour qu'il puisse être correctement lu.

Il est envisageable d'utiliser confortablement des QR-codes complexes tant que ceux-ci peuvent toujours être affichés à une taille suffisante pour être lus correctement. Les données peuvent être compressées et contenir des payloads avec uniquement des informations utiles lors de la lecture du code QR.

L'utilisation des QR-Codes dynamiques permet également de réduire la tailles des données stockées.

#### 3.2.1 QR-Codes dynamiques

Les codes QR statiques sont utiles dans les situations où le code QR n'a pas besoin d'être mis à jour.

Les codes QR dynamiques sont modifiables et offrent plus de fonctionnalités que les codes QR statiques. En effet, ils ont une URL courte intégrée dans le code qui peut rediriger l'utilisateur vers l'URL du contenu. Ainsi l'url à laquelle rediriger l'utilisateur peut être modifiée même après que le QR-Code ait été généré. De plus, cela permet d'obtenir les statistiques de scan telles que la date et le lieu de chaque scan, leur nombre total ou encore le système d'exploitation de l'appareil lisant le code.

Pour utiliser cela, il est nécessaire de pouvoir atteindre l'URL qui nous redirigera vers la ressource voulue. Ainsi, dans le cadre du développement mobile ce n'est pas adapté si l'application doit pouvoir être utilisée en l'absence d'une connexion internet.

### 4. iBeacons

Les iBeacons sont très souvent présentés comme une alternative à NFC.

Cette affirmation n'est pas correcte. En effet, il est possible de trouver des cas d'utilisation où le choix entre un iBeacon et un tag NFC n'a que peu d'importance : 

ex : accéder aux horaires à un arrêt de bus 

- Via NFC : scan d'un tag à l'arrêt de bus
- Via iBeacon : utilisation d'une application qui localise l'utilisateur et affiche les horaires en fonction de l'iBeacon rencontré

Cependant, d'autres cas d'utilisation ne permettent pas de choisir entre un iBeacon et un tag NFC :

ex : second facteur d'authentification

- Via NFC : scan d'un tag (seul le possesseur du tag peut scanner le message). Le tag peut être protégé des clones avec les mécanismes énumérés au point 2.4.1.
- Via IBeacon : pas possible car il peut être cloné (utilisation du même uuid ) et utilisé à un autre endroit (Spoofing)

autre exemple : Piggybacking

De plus les iBeacons ont l'avantage de permettre à un utilisateur d'avoir une application sensible au contexte de son utilisation (context-awereness) sans qu'il ne s'en rende compte. En effet, avec un tag NFC, il est nécessaire que l'utilisateur scanne le tag pour utiliser ses informations.

**En résumé :** certains cas d'utilisations permettent de choisir entre un tag NFC ou un iBeacon mais d'autres cas d'utilisation restreignent l'utilisation d'une technologie au profit de l'autre.