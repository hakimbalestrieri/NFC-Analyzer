# :iphone:SYM - Labo 3 - Environnement

:bust_in_silhouette: **Auteurs :** `Alexis Allemann` ,  `Hakim Balestrieri` et `Christian Gomes`

:page_facing_up: **En bref :** Ce repository contient la r�alisation du laboratoire nomm� "Environnement" de l'[HEIG-VD](https://heig-vd.ch/). Ce travail est r�alis� durant le cours de SYM.

:dart: **Objectifs :** Ce travail est constitu� de manipulations qui vont nous permettre de nous familiariser avec l�utilisation de donn�es environnementales. Celui-ci est divis� en deux laboratoires : dans cette premi�re partie nous nous int�resserons aux codes-barres, aux balises NFC et aux iBeacons

## R�ponse aux questions:writing_hand:

### 2. NFC

#### 2.4.1 Clonage d'un tag NFC

Pour emp�cher une personne malveillante de copier les valeurs stock�es dans un tag NFC, il existes plusieurs m�thodes :

De nombreuses �tiquettes NFC contiennent �galement un identifiant unique qui est pr�programm� par le fabricant du tag et qui ne peut pas �tre modifi� sur les tags normaux. Ainsi, il serait possible de cr�er une signature num�rique bas�e sur l'identifiant du tag et ses donn�es. Cependant, toutes les donn�es peuvent �tre extraites de l'�tiquette et il serait possible de cr�er une �tiquette avec l'identifiant du tag en utilisant du mat�riel sp�cialis� (proc�dure relativement complexe).

Une autre solution plus s�re pourrait �tre l'utilisation d'un tag qui contient une cl� asym�trique secr�te et qui fournit une fonction pour signer un d�fi  cryptographique. Dans ce cas, pour v�rifier l'authenticit� du tag, on lui envoie un d�fi al�atoire qu'il doit signer et on v�rifie le r�sultat du chiffrement.

Finalement, il existe une balise nomm�e `NTAG 413` qui peut g�n�rer un nouveau message NDEF � chaque fois qu'elle est utilis�e (en utilisant un chiffrement AES). Cela permet d'incorporer le cryptage dans l'URL d'un NDEF et le serveur h�te peut le crypter avec la m�me cl�. En cas de copie, le serveur le reconna�tra. D'autres types de balises (ex : `NTAG 424`) utilisent le m�me principe.

Cela n�cessite � un tiers (ici le serveur) d'authentifier les beacons.

> Source des informations trouv�es : https://seritag.com/learn/using-nfc/nfc-tag-authentication-explained

#### 2.4.2 Utilisation d'un iBeacon

L'utilisation d'un iBeacon pour attester que l'utilisateur poss�de le tag est une solution peu efficace car le signal de l'iBeacon peut �tre lu par un attaquant et copi�. En effet, il est tr�s facile de simuler des iBeacons gr�ce � des applications mobiles. De plus, si l'attaquant se trouve � proximit� du possesseur de l'iBeacon et qu'il est en mesure de recevoir les fr�quences �mises par ce dernier, alors il pourra alors tout de m�me s'authentifier.

### 3. QR-Codes

#### 3.2.1 Quantit� maximale de donn�es dans un QR-Code

Voici les limitations de tailles des donn�es qu'un code QR peut contenir : 

| Type des donn�es | Longueur maximale               |
| ---------------- | ------------------------------- |
| Num�rique        | 7089                            |
| Alphanum�rique   | 4296                            |
| Binaire / Byte   | 2953 (8-bit bytes) (23624 bits) |

> Sp�cification ISO d'un code QR : https://www.iso.org/standard/62021.html
>
> Source des informations trouv�es : https://en.wikipedia.org/wiki/QR_code

Plus la taille des donn�es stock�es dans un code QR est grand, plus le code-barre devient complexe. La taille du code QR d�pend des donn�es qu'il contient. Si celui-ci en contient beaucoup, alors il est n�cessaire d'afficher celui-ci en grand pour qu'il puisse �tre correctement lu.

Il est envisageable d'utiliser confortablement des QR-codes complexes tant que ceux-ci peuvent toujours �tre affich�s � une taille suffisante pour �tre lus correctement. Les donn�es peuvent �tre compress�es et contenir des payloads avec uniquement des informations utiles lors de la lecture du code QR.

L'utilisation des QR-Codes dynamiques permet �galement de r�duire la tailles des donn�es stock�es.

#### 3.2.1 QR-Codes dynamiques

Les codes QR statiques sont utiles dans les situations o� le code QR n'a pas besoin d'�tre mis � jour.

Les codes QR dynamiques sont modifiables et offrent plus de fonctionnalit�s que les codes QR statiques. En effet, ils ont une URL courte int�gr�e dans le code qui peut rediriger l'utilisateur vers l'URL du contenu. Ainsi l'url � laquelle rediriger l'utilisateur peut �tre modifi�e m�me apr�s que le QR-Code ait �t� g�n�r�. De plus, cela permet d'obtenir les statistiques de scan telles que la date et le lieu de chaque scan, leur nombre total ou encore le syst�me d'exploitation de l'appareil lisant le code.

Pour utiliser cela, il est n�cessaire de pouvoir atteindre l'URL qui nous redirigera vers la ressource voulue. Ainsi, dans le cadre du d�veloppement mobile ce n'est pas adapt� si l'application doit pouvoir �tre utilis�e en l'absence d'une connexion internet.

### 4. iBeacons

Les iBeacons sont tr�s souvent pr�sent�s comme une alternative � NFC.

Cette affirmation n'est pas correcte. En effet, il est possible de trouver des cas d'utilisation o� le choix entre un iBeacon et un tag NFC n'a que peu d'importance : 

ex : acc�der aux horaires � un arr�t de bus 

- Via NFC : scan d'un tag � l'arr�t de bus
- Via iBeacon : utilisation d'une application qui localise l'utilisateur et affiche les horaires en fonction de l'iBeacon rencontr�

Cependant, d'autres cas d'utilisation ne permettent pas de choisir entre un iBeacon et un tag NFC :

ex : second facteur d'authentification

- Via NFC : scan d'un tag (seul le possesseur du tag peut scanner le message). Le tag peut �tre prot�g� des clones avec les m�canismes �num�r�s au point 2.4.1.
- Via IBeacon : pas possible car il peut �tre clon� (utilisation du m�me uuid ) et utilis� � un autre endroit (Spoofing)

autre exemple : Piggybacking

De plus les iBeacons ont l'avantage de permettre � un utilisateur d'avoir une application sensible au contexte de son utilisation (context-awereness) sans qu'il ne s'en rende compte. En effet, avec un tag NFC, il est n�cessaire que l'utilisateur scanne le tag pour utiliser ses informations.

**En r�sum� :** certains cas d'utilisations permettent de choisir entre un tag NFC ou un iBeacon mais d'autres cas d'utilisation restreignent l'utilisation d'une technologie au profit de l'autre.