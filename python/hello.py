from com.alec.ttalk.autotalk.interfaces import AutoTalkType

class AutoTalk(AutoTalkType):
    def __init__(self):
        pass

    def sendMessage(self, jid, name, message, mod, inf):
        self.jid = jid
        self.name = name
        self.message = message
        self.mod = mod
        self.inf = inf
        return 'Hello! ' + self.name 

    def getMode(self):
        return self.mod

    def getInfo(self):
        return self.inf