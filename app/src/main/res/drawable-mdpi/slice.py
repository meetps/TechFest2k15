from PIL import Image
import Image
import os

def crop(Path,input,height,width,i,k,page,area):
    im = Image.open(input)
    imgwidth, imgheight = im.size
    for i in range(0,imgheight,height):
        for j in range(0,imgwidth,width):
            box = (j, i, j+width, i+height)
            a = im.crop(box)
            a.load()
            try:
                o = a.crop(area)
                o.save(os.path.join(Path,"PNG","%s" % page,"IMG-%s.png" % k))
                print "Saved File"
            except:
                pass
            k +=1

image = Image.open("selected_icons.png")
imgwidth, imgheight = image.size
box1 = (0,0,50,45)
box2 = (0,45,50,90)
box3 = (0,90,50,135)
box4 = (0,135,50,180)
box5 = (0,180,50,225)
box6 = (0,225,50,270)
box7 = (0,270,50,315)
box8 = (0,315,50,360)
box9 = (0,360,50,404)
a1 = image.crop(box1)
a2 = image.crop(box2)
a3 = image.crop(box3)
a4 = image.crop(box4)
a5 = image.crop(box5)
a6 = image.crop(box6)
a7 = image.crop(box7)
a8 = image.crop(box8)
a9 = image.crop(box9)
a1.save("initiatives.png","PNG")
a2.save("ideate.png","PNG")
a3.save("competitions.png","PNG")
a4.save("workshops.png","PNG")
a5.save("lectures.png","PNG")
a6.save("conferences.png","PNG")
a7.save("exhibitions.png","PNG")
a8.save("technoholix.png","PNG")
a9.save("ozone.png","PNG")